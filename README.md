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
* Add Spring container implementation.
* Add Database capability to store user data
* Add NO-SQL capability such as cassandra to store message
* Frequent visit will be forbidden or blocked

How to get:
* If you have git, please git clone https://github.com/zecbit/ChatServer.git

How to run the Server:
* If you have IDE(such as IntelliJ), run/debug SecureChatServer class
* If you have JDK(1.7 above), run maven, then run java -jar ChatServer-1.0-SNAPSHOT.jar
* If you have docker

How to run the Client:
* If you have IDE(such as IntelliJ), run/debug SecureChatClient class

How to configure:
* edit system parameter in config.properties within folder $ChatServer$/src/main/resources
* edit message in messages.properties within folder $ChatServer$/src/main/resources