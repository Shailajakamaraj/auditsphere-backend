package com.auditsphere.auditspherebackend.util;

import com.auditsphere.auditspherebackend.entity.Role;
import com.auditsphere.auditspherebackend.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    public static boolean hasExcelFormat(MultipartFile file) {

        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                .equals(file.getContentType());

    }

    public static List<User> excelToUsers(
            InputStream inputStream,
            PasswordEncoder passwordEncoder
    ) {

        try {

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            List<User> users = new ArrayList<>();

            int rowNumber = 0;

            for (Row row : sheet) {

                if (rowNumber == 0) {

                    rowNumber++;
                    continue;

                }

                User user = new User();

                user.setName(
                        row.getCell(0).getStringCellValue()
                );

                user.setEmail(
                        row.getCell(1).getStringCellValue()
                );

                user.setPassword(
                        passwordEncoder.encode(
                                row.getCell(2).getStringCellValue()
                        )
                );

                user.setRole(
                        Role.valueOf(
                                row.getCell(3)
                                        .getStringCellValue()
                                        .toUpperCase()
                        )
                );

                users.add(user);

            }

            workbook.close();

            return users;

        }

        catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse Excel file."
            );

        }

    }

}