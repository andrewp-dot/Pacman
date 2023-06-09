COMPILER=javac
EXEC=java

# locate javafx on macos
PATH_TO_FX=./lib/JavaFX.framework/javafx-sdk-17.0.7/lib
FLAGS=--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics

# project structure variables
SRC=src
MAIN=Main
MAIN_PATH=./$(SRC)/$(MAIN)
MAIN_JAVA=$(MAIN_PATH).java
MAIN_CLASS=$(MAIN_PATH).class

# constants
PROJECT_ZIP_NAME=xponec01

make: $(MAIN_JAVA) clean
	$(COMPILER) $(FLAGS) -cp  $(SRC) $(MAIN_JAVA)

run: make
	$(EXEC) $(FLAGS) -cp .:$(SRC) $(MAIN)

.PHONY:
clean:
	find ./$(SRC) -type f -name '*.class' -delete

zip:
	zip -r ${PROJECT_ZIP_NAME}.zip src doc requirements.pdf readme.txt lib data pom.xml


