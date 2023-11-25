# LibrarySystem
Library system written in Java + JDBC (MySQL).

### Initialize database
Below is a `docker-compose.yml` file that will help you do this:

```yml
# Created: 25.11.23 22:20 pm.
# Author: (https://github.com/kh0mka)

version: '3.8'

services:

  database-mysql:
    image: mysql:8
    container_name: database-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: rootpasswd
      MYSQL_DATABASE: library
      MYSQL_USER: mysqlusername
      MYSQL_PASSWORD: mysqlpasswd
    ports:
      - "3306:3306"
    command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
