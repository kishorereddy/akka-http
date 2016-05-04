# Akka HTTP REST Example 

Simple examples of building Rest api in Akka Http using Scala.

# Examples
This sample app shows simple example of how to do the following in Akka Http

- Route setup via cases
- Route setup via route tree
- Route setup via chaining
- Route setup via model / crud
- Authentication
- Web Api 
- Http Get
- Http Post
- Return text/html
- Return json


# Setup
- Option 1: Run the app in intellij
- Option 2: Run "sbt run" on command line in root directory
- 

# Output
There are several routes available to show examples of Akka Http usage.
The following route ( accessed via http post - using Postman ) will provide a summary of your app, host, and scala runtime.

http://localhost:9911/admin/status/info

```scala
ABOUT	===================================
desc	Show various ways to setup routes, web apis, and some general patterns and practices
name	Sample Akka Http Web Api
url	http://www.mycompany.com
tags	scala,akka,akka-http,web,apis,routes
contact	kishore@abc.com
version	1.0.1.7
region	n/a
group	your group
HOST	===================================
name	KRPC1
ip	Windows 8.1
version	6.3
origin	local
arch	amd64
ext1	C:\Users\kv\AppData\Local\Temp\
LANG	===================================
name	scala
versionNum	2.11.6
home	C:/Tools/Java/jdk1.8.0_91/jre
version	1.8.0_91
origin	local
ext1	
```

# Copyright

Copyright (C) 2016 Kishore Reddy

Distributed under the MIT License.
