# Sysc4806 Group 42
### Memebers
- Derek Stride
- Justin Fleming
- Justin Krol
- Noah Segal

### Project
We will be doing the 4th year project site.

### [Backlog](https://github.com/JFleming4/FourthYearProjectSite/projects/1#column-2280177)
#### Issues for Milestone 1 Complete
- [x] [Issue #20 Project Objects + Test](https://github.com/JFleming4/FourthYearProjectSite/pull/20)
- [x] [Issue #16 Configure Postgresql on Heroku](https://github.com/JFleming4/FourthYearProjectSite/issues/16)
- [x] [Issue #21 Add an initial landing page](https://github.com/JFleming4/FourthYearProjectSite/issues/21)
- [x] [Issue #10 Prof Entity](https://github.com/JFleming4/FourthYearProjectSite/issues/10)
- [x] [Issue #9 Student Entity](https://github.com/JFleming4/FourthYearProjectSite/issues/9)
- [x] [Issue #8 Project Model Entity](https://github.com/JFleming4/FourthYearProjectSite/issues/8)
- [x] [Issue #7 Set up database Models](https://github.com/JFleming4/FourthYearProjectSite/issues/7)
- [x] [Issue #6 Student Menu Page](https://github.com/JFleming4/FourthYearProjectSite/issues/6)    
- [x] [Issue #5 Project Model](https://github.com/JFleming4/FourthYearProjectSite/issues/5)
- [x] [Issue #4 Project Coordinator Model](https://github.com/JFleming4/FourthYearProjectSite/issues/4)
- [x] [Issue #3 Student Model](https://github.com/JFleming4/FourthYearProjectSite/issues/3)
- [x] [Issue #2 Prof Model](https://github.com/JFleming4/FourthYearProjectSite/issues/2)
- [x] [Issue #29 Deploy to Heroku](https://github.com/JFleming4/FourthYearProjectSite/issues/29)
#### Issues for Milestone 2
- [ ] [Issue #11 File Attatchments Entity](https://github.com/JFleming4/FourthYearProjectSite/issues/11)
- [ ] [Issue #15 Oral Presentation Availability Picker](https://github.com/JFleming4/FourthYearProjectSite/issues/15)
- [ ] [Issue #14 Prof menu](https://github.com/JFleming4/FourthYearProjectSite/issues/14)
- [ ] [Issue #13 File Uploads](https://github.com/JFleming4/FourthYearProjectSite/issues/13)
- [ ] [Issue #12 Authentication and Authorization](https://github.com/JFleming4/FourthYearProjectSite/issues/12)
#### Unassigned issues


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

By default, this will run the server on `http://localhost:8080`.
