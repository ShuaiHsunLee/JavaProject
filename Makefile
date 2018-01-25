content="Main-Class: MyPackage.project"
content1="Main-Class: MyPackage.project1"

compile1:
	@javac -d java/bin1 java/src1/*.java 2>&1
	@echo $(content) > java/bin1/Manifest.txt
	@cd java/bin1 && jar cfm MyJar1.jar Manifest.txt MyPackage/*.class && mv MyJar1.jar ../..

compile2:
	@javac -d java/bin2 java/src2/*.java 2>&1
	@echo $(content) > java/bin2/Manifest.txt
	@cd java/bin2 && jar cfm MyJar2.jar Manifest.txt MyPackage/*.class && mv MyJar2.jar ../..

partialcompile1:
	@javac -d java/bin1 java/src1/project.java 2>&1
	@echo $(content) > java/bin1/Manifest.txt
	@cd java/bin1 && jar cfm MyJar1.jar Manifest.txt MyPackage/*.class && mv MyJar1.jar ../..

compile:
	@javac -d java1/bin java1/src/*.java 2>&1
	@echo $(content1) > java1/bin/Manifest.txt
	@cd java1/bin && jar cfm MyJar.jar Manifest.txt MyPackage/*.class && mv MyJar.jar ../..

run:
	@java -jar MyJar.jar