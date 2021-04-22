# Allegro software engineer intern exercise

## About exercise
The main goal of the exercise is to make an application, that allows you to list GitHub repositories of specified users (names and stars) and to get the total number of stars of a certain user. This data is supposed to be retrieved via HTTP protocol.

## How to run application 

Yet to do

## About an application

Application has two main endpoints, through which user can get certain data. They are

- '/repos/{user}' - returns list of repositories of a specified in '{user}' place user. They are returned in a form of JSON with two keys: name and stars.
- '/stars/{user}' - returns the total number of stars of a specified in '{user}' place user. They are returned in a form of a single value.
