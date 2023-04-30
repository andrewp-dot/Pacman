COMPILER=javac
EXEC=java

# locate javafx on macos
PATH_TO_FX=./JavaFX.framework/javafx-sdk-17.0.7/lib
FLAGS=--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics

# project structure variables
SRC=src
MAIN=Main
MAIN_PATH=./$(SRC)/$(MAIN)
MAIN_JAVA=$(MAIN_PATH).java
MAIN_CLASS=$(MAIN_PATH).class

# constants
PROJECT_ZIP_NAME=ija_project

make: $(MAIN_JAVA)
	$(COMPILER) $(FLAGS) -cp  $(SRC) $(MAIN_JAVA)

run: make
	$(EXEC) $(FLAGS) -cp .:$(SRC) $(MAIN)

.PHONY:
clean:
	rm $(SOURCES) ./$(SRC)/*.class ./$(SRC)/model/*class ./$(SRC)/view/*.class ./$(SRC)/controller/*.class

zip:
	zip -r ${PROJECT_ZIP_NAME}.zip Makefile doc src JavaFX.framework 
