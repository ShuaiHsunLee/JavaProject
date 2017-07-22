content="Main-Class: MyPackage.project1"

compile:
	@javac -d bin src/*.java 2>&1
	@echo $(content) > bin/Manifest.txt
	@cd bin && jar cfm MyJar.jar Manifest.txt MyPackage/*.class #&& mv MyJar1.jar ../..

run:
	@cd bin && java -jar MyJar.jar