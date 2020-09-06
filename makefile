JFLAGS = -d .
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        src/MaxFibonacciHeap/Node.java \
        src/MaxFibonacciHeap/Implement.java \
        src/hashtagcounter.java

default: classes
	@echo Input either "-java hashtagcounter <input_file> <output_file>"
	@echo or "-java hashtagcounter <input_file>"
	@echo Results will be shown on the console if the output_file is not specified 

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

