<h1>Reactive Fodmap App APIs</h1>


You want to throw a Runtime exception when the user does not pass any group name in the request
One way to handle no group type is to return everything which seems too much of an intensive operation
By throwing runtime exception, the user has to make another request for information. We dont want the exception
to be passed to the user, they will not understand, we need human readable message

should verify if group type is one of acceptable group types


FodmapService returns empty Flux if not records in database found. Not need to throw exception

Converted the group type parameter into a enum. easier for testing less likely to get wrong


To start up the embedded cassandra. Start the main method inside the EmbeddedCassandra class. In your terminal execute
cqlsh localhost 9142. This will start a cassandra shell inside the embedded cassandra running on the jvm. To see the process
running on a particular port, execute lsof -i tcp:9142


have the exception thrown to be more specific