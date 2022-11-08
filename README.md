## Protocol
1) Client Connects to the server
2) Client sends "CLIENT_INFO_PACKET"
3) Server responds with "SERVER_OKAY" or "SERVER_ERROR"
4) If "SERVER_OKAY" no problems
5) if "SERVER_ERROR" and the error code is 1, then user tries to send another "CLIENT_INFO_PACKET" with a different username.
6) Client Connection with the server is established.

#### CLIENT_INFO_PACKET
- username
- other information maybe added

#### SERVER_OKAY
- no information

#### SERVER_ERROR
- error code
- description
- codes
  - 1 - Username not unique
  - error codes to be added
  - N - Internal Error