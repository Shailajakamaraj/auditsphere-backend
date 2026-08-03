FROM ubuntu:latest
LABEL authors="shail"

ENTRYPOINT ["top", "-b"]