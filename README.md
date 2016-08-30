# rc-robot-server
Java server for Arduino interface

TCP server with simple command protocol.

  +--------------+         +---------------+           +-----------+
  |              |   tcp   |   TCP Server  |   uart    |           |
  |  TCP Client  |---------|eg. Raspberry  |-----------|  Arduino  |
  |              |         |               |           |           |
  +--------------+         +---------------+           +-----------+
