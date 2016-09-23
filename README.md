This is a high-performance and high-scalability java nio socket servers and clients with support of netty.

Feature
* Use netty framework(4.1.0) to support high-performance, high-scalability protocol servers and clients.
* Message with SSL encryption and decryption
* User can use unique name to login, create chat room, join chat room and leave chat room.
* Message over 8192(configurable in config.properties) is forbidden.
* Empty message are not allowed.
* ip, port configurable
* message can be configured in properties to support i18n

Further improvement not implemented:
1. Add Spring container implementation.
2. Add Database capability to store user data
3. Add NO-SQL capability such as cassandra to store message
4. Frequent visit will be forbidden or blocked