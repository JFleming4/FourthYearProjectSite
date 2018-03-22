# Sysc4806 Group 42

[![Build Status](https://travis-ci.org/JFleming4/FourthYearProjectSite.svg?branch=master)](https://travis-ci.org/JFleming4/FourthYearProjectSite)

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
#### Issues for Milestone 2 Complete
- [x] [Issue #11 File Attatchments Entity](https://github.com/JFleming4/FourthYearProjectSite/issues/11)
- [x] [Issue #14 Prof menu](https://github.com/JFleming4/FourthYearProjectSite/issues/14)
- [x] [Issue #13 File Uploads](https://github.com/JFleming4/FourthYearProjectSite/issues/13)
- [x] [Issue #41 Ensure Availability is Persisted](https://github.com/JFleming4/FourthYearProjectSite/issues/41)
- [x] [Issue #39 Create Oral Presentation View](https://github.com/JFleming4/FourthYearProjectSite/issues/39)
- [x] [Issue #14 Prof Menu](https://github.com/JFleming4/FourthYearProjectSite/issues/14)
- [x] [PR #44 Presentation Template](https://github.com/JFleming4/FourthYearProjectSite/pull/44)
- [x] [PR #46 Professor & Project Page](https://github.com/JFleming4/FourthYearProjectSite/pull/46)
- [x] [PR #43 File Uploads for Prosal and Final report](https://github.com/JFleming4/FourthYearProjectSite/pull/43)
- [x] [Issue #33 Set up S3 buckets and configure heroku to access them](https://github.com/JFleming4/FourthYearProjectSite/issues/33)
- [x] [Issue #34 Setup a local storage bucket API for local dev and test](https://github.com/JFleming4/FourthYearProjectSite/issues/34)
- [x] [PR #32 File Attachment Entity](https://github.com/JFleming4/FourthYearProjectSite/pull/32)
- [x] [Issue #52 Move Malformed TimeSlot Exception to Constructor](https://github.com/JFleming4/FourthYearProjectSite/issues/52)

#### Issues for Milestone 3
- [ ] [Issue #12 Authentication and Authorization](https://github.com/JFleming4/FourthYearProjectSite/issues/12)
- [ ] [Issue #15 Oral Presentation Availability Picker](https://github.com/JFleming4/FourthYearProjectSite/issues/15)
- [ ] [Issue #40 Create Logic for picking a time that works for professor and students](https://github.com/JFleming4/FourthYearProjectSite/issues/40)
- [ ] [Issue #50 Add Integration Tests for HomeController](https://github.com/JFleming4/FourthYearProjectSite/pull/50)
- [ ] [Issue #49 Add Bootstrap, main.css, and header+footer fragments](https://github.com/JFleming4/FourthYearProjectSite/pull/49)
- [ ] [Issue #48 Attach AutherizedUser records to Existing student/professor records](https://github.com/JFleming4/FourthYearProjectSite/pull/48)
- [ ] [Issue #47 Add basic authentication](https://github.com/JFleming4/FourthYearProjectSite/pull/47)
- [ ] [Issue #55 Enable profs to create, archive, delete, and edit projects](https://github.com/JFleming4/FourthYearProjectSite/issues/55)
- [ ] [Issue #54 Only allow the project coordinator to create prof accounts](https://github.com/JFleming4/FourthYearProjectSite/issues/54)
- [ ] [Issue #53 Uploaded files with the same name delete previous versions](https://github.com/JFleming4/FourthYearProjectSite/issues/53)
- [ ] [Issue #51 Change Project and professor controller to search by Id instead of grab first](https://github.com/JFleming4/FourthYearProjectSite/issues/51)

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
$ export SYSC_DATABASE_URL="jdbc:postgresql://localhost/project_manager_dev" # do not change this
$ export SYSC_DATABASE_USERNAME="myusername" # change this to your postgres username
$ export SYSC_DATABASE_PASSWORD="mypassword" # change this to "" or the password you set
```

#### Run the server
This must be done from the same console in which you set your environment variables.

```bash
$ mvn spring-boot:run
```

By default, this will run the server on `http://localhost:8080`.
