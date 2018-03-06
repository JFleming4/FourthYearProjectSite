# Sysc4806 Group 42
### Memebers
- Derek Stride
- Justin Fleming
- Justin Krol
- Noah Segal

### Project
We will be doing the 4th year project site.

### Backlog


### Setup

#### Requirements
Tested on MacOS 10.13.3
1. IntelliJ IDEA with Maven
2. JDK 1.8
3. Homebrew

#### Clone the project
Clone the project using the link [here](https://github.com/JFleming4/FourthYearProjectSite).

#### Resolve Maven dependencies
Resolve dependencies using Maven (this can be done in IntelliJ).

#### Install Postgresql

```bash
$ brew install postgres
```

To see which users exist for your postgres server:
```bash
$ psql postgres
postgres=# \du
```
After installing, you may already have a couple of users by default:
```
                                    List of roles
 Role name  |                         Attributes                         | Member of
------------+------------------------------------------------------------+-----------
 myusername | Superuser, Create role, Create DB                          | {}
 postgres   | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
```

#### Create the Postgres DB

```bash
$ psql postgres
postgres=# CREATE DATABASE project_manager_dev;
```

#### Set up environment variables
Note: unless you've changed it, the password will probably be `""`.

```bash
$ export SYSC_DATABASE_URL="jdbc:postgresql://localhost/project_manager_dev"
$ export SYSC_DATABASE_USERNAME="myusername"
$ export SYSC_DATABASE_PASSWORD="mypassword"
```

#### Run the server
This must be done from the same console in which you set your environment variables.

```bash
$ mvn spring-boot:run
```
