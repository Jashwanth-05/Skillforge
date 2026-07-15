# SkillForge API Contract

## Overview

This document defines the REST API contract for the SkillForge MVP.

The purpose of this document is to establish a clear agreement between the frontend and backend before implementation begins. It describes every REST endpoint, request and response format, authentication mechanism, authorization rules, validation requirements, and HTTP status codes.

---

# API Information

| Property | Value |
|----------|-------|
| API Name | SkillForge REST API |
| Version | v1 |
| Base URL | `/api/v1` |
| Protocol | HTTPS |
| Data Format | JSON |
| Authentication | JWT Bearer Token |

---

# Content Type

All requests and responses use JSON.

```
Content-Type: application/json
Accept: application/json
```

---

# Authentication

SkillForge uses JWT (JSON Web Token) authentication.

Protected endpoints require the following header:

```
Authorization: Bearer <JWT_TOKEN>
```

Public endpoints:

- POST /api/v1/auth/register
- POST /api/v1/auth/login

All remaining endpoints require authentication.

---

# User Roles

| Role | Permissions |
|------|-------------|
| USER | Solve problems, submit code, view dashboard |
| ADMIN | All USER permissions + Manage problems |

---

# Standard Response Format

Every API follows the same response structure.

## Success Response

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {}
}
```

---

## Error Response

```json
{
  "success": false,
  "message": "Validation failed",
  "errors": {
    "field": "Error message"
  }
}
```

---

# HTTP Status Codes

| Code | Meaning |
|------|----------|
|200|OK|
|201|Created|
|204|No Content|
|400|Bad Request|
|401|Unauthorized|
|403|Forbidden|
|404|Not Found|
|409|Conflict|
|500|Internal Server Error|

---

# Authentication Module

---

## Register User

### Endpoint

```
POST /api/v1/auth/register
```

### Description

Registers a new user account.

### Authentication

Not Required

### Request Body

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "Password123"
}
```

### Validation Rules

| Field | Rules |
|--------|-------|
|name|Required, 3-50 characters|
|email|Required, valid email, unique|
|password|Required, minimum 8 characters|

### Success Response

Status

```
201 Created
```

```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER"
  }
}
```

### Error Responses

#### Validation Failed

```
400 Bad Request
```

```json
{
  "success": false,
  "message": "Validation failed",
  "errors": {
    "email": "Invalid email format"
  }
}
```

#### Email Already Exists

```
409 Conflict
```

```json
{
  "success": false,
  "message": "Email already registered"
}
```

---

## Login

### Endpoint

```
POST /api/v1/auth/login
```

### Description

Authenticates a user and returns a JWT token.

### Authentication

Not Required

### Request Body

```json
{
  "email": "john@example.com",
  "password": "Password123"
}
```

### Success Response

```
200 OK
```

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "<JWT_TOKEN>"
  }
}
```

### Error Response

```
401 Unauthorized
```

```json
{
  "success": false,
  "message": "Invalid email or password"
}
```

---

# User Module

---

## Get Current User

### Endpoint

```
GET /api/v1/users/me
```

### Description

Returns the currently authenticated user's profile.

### Authentication

Required

### Authorization

USER

ADMIN

### Success Response

```
200 OK
```

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER"
  }
}
```

### Error Responses

```
401 Unauthorized
```

```
404 Not Found
```

---

## Get Current User Submission History

### Endpoint

```
GET /api/v1/users/me/submissions
```

### Description

Returns paginated submission history of the logged-in user.

### Authentication

Required

### Query Parameters

| Parameter | Description |
|-----------|-------------|
|page|Page Number|
|size|Records Per Page|

Example

```
GET /api/v1/users/me/submissions?page=0&size=10
```

### Success Response

```json
{
  "success": true,
  "data": [
    {
      "submissionId": 12,
      "problemTitle": "Two Sum",
      "status": "ACCEPTED",
      "language": "JAVA",
      "submittedAt": "2026-07-07T10:00:00"
    }
  ]
}
```

---

# Problem Module

---

## Get All Problems

### Endpoint

```
GET /api/v1/problems
```

### Description

Returns all coding problems.

### Authentication

Required

### Query Parameters

| Parameter | Description |
|-----------|-------------|
|page|Page Number|
|size|Page Size|
|difficulty|EASY, MEDIUM, HARD|
|search|Search by title|

Example

```
GET /api/v1/problems?page=0&size=20&difficulty=EASY
```

### Success Response

```
200 OK
```

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "title": "Two Sum",
      "difficulty": "EASY"
    }
  ]
}
```

---

## Get Problem Details

### Endpoint

```
GET /api/v1/problems/{id}
```

### Description

Returns complete problem details.

### Authentication

Required

### Success Response

```json
{
  "success": true,
  "data": {
    "id": 1,
    "title": "Two Sum",
    "description": "...",
    "difficulty": "EASY",
    "timeLimit": 1,
    "memoryLimit": 256,
    "sampleInput": "...",
    "sampleOutput": "..."
  }
}
```

---

## Create Problem

### Endpoint

```
POST /api/v1/problems
```

### Authentication

Required

### Authorization

ADMIN

### Request Body

```json
{
  "title": "Two Sum",
  "description": "Find two numbers...",
  "difficulty": "EASY",
  "timeLimit": 1,
  "memoryLimit": 256,
  "testCases": [
    {
      "input": "2 7 11 15",
      "expectedOutput": "0 1",
      "hidden": false
    }
  ]
}
```

### Success Response

```
201 Created
```

---

## Update Problem

### Endpoint

```
PUT /api/v1/problems/{id}
```

### Authentication

Required

### Authorization

ADMIN

### Request Body

Same as Create Problem.

### Success Response

```
200 OK
```

---

## Delete Problem

### Endpoint

```
DELETE /api/v1/problems/{id}
```

### Authentication

Required

### Authorization

ADMIN

### Success Response

```
204 No Content
```

---

# Submission Module

---

## Run Code

### Endpoint

```
POST /api/v1/submissions/run
```

### Description

Runs code only against public sample test cases.

### Authentication

Required

### Request Body

```json
{
  "problemId": 1,
  "language": "JAVA",
  "sourceCode": "public class Solution {...}"
}
```

### Success Response

```json
{
  "success": true,
  "data": {
    "status": "PASSED",
    "executionTime": 12,
    "memoryUsed": 18,
    "output": "0 1"
  }
}
```

---

## Submit Code

### Endpoint

```
POST /api/v1/submissions/submit
```

### Description

Runs the solution against all hidden test cases and stores the submission.

### Authentication

Required

### Request Body

```json
{
  "problemId": 1,
  "language": "JAVA",
  "sourceCode": "public class Solution {...}"
}
```

### Success Response

```json
{
  "success": true,
  "message": "Submission completed",
  "data": {
    "submissionId": 15,
    "status": "ACCEPTED",
    "executionTime": 10,
    "memoryUsed": 17
  }
}
```

---

## Get Submission Details

### Endpoint

```
GET /api/v1/submissions/{id}
```

### Description

Returns complete details of a submission.

### Authentication

Required

### Success Response

```json
{
  "success": true,
  "data": {
    "submissionId": 15,
    "problemTitle": "Two Sum",
    "language": "JAVA",
    "status": "ACCEPTED",
    "executionTime": 10,
    "memoryUsed": 17,
    "submittedAt": "2026-07-07T10:00:00",
    "sourceCode": "public class Solution {...}"
  }
}
```

---

# Dashboard Module

---

## Get Dashboard Statistics

### Endpoint

```
GET /api/v1/dashboard
```

### Description

Returns user statistics displayed on the dashboard.

### Authentication

Required

### Success Response

```json
{
  "success": true,
  "data": {
    "totalSolved": 135,
    "totalSubmissions": 240,
    "acceptanceRate": 56.25
  }
}
```

---

# DTO Mapping (Planned)

## Authentication

- RegisterRequest
- LoginRequest
- LoginResponse

## User

- UserResponse

## Problem

- ProblemRequest
- ProblemResponse
- ProblemSummaryResponse
- TestCaseRequest

## Submission

- RunCodeRequest
- SubmitCodeRequest
- SubmissionResponse
- SubmissionHistoryResponse

## Dashboard

- DashboardResponse

---

# Future API Modules

The following APIs are planned for future releases and are intentionally excluded from MVP implementation.

- Contest APIs
- Leaderboard APIs
- AI Interview APIs
- Resume Analysis APIs
- Recommendation APIs
- Notification APIs

---

# Version History

| Version | Date | Description |
|----------|------|-------------|
| v1 | Initial MVP | Authentication, User, Problem, Submission, Dashboard APIs |