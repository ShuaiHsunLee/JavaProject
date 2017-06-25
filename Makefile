content="Main-Class: MyPackage.project"

compile:
	@javac -d bin src/*.java 2>&1
	@echo $(content) > bin/Manifest.txt
	@cd bin && jar cfm MyJar.jar Manifest.txt MyPackage/*.class

run:
	@cd bin && java -jar MyJar.jar