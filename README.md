# ada2fuml

This project reads Ada specification files (*.ads) in a folder and produces fUML XMI that is compatible with MagicDraw.

## Prerequisites

- Java 1.8 (or above)
- **gnat2xml.exe** in the **PATH** variable.

## Usage

Just download the latest jar file from [here](https://github.com/hergin/ada2fuml/releases) and put it in the same folder where you *.ads files stay. Run it like below by command line (assuming java is the executable java of version 1.8 or above)

> java -jar ada2fuml.jar

## Console Logs

The output is printed to the console. You can save it to a file using the command below to send it by email.

> java -jar ada2fuml.jar > logs.txt

## Thanks

This project is implemented with the support provided by Raytheon Company.

## Contributors

- Main design & code: [Huseyin Ergin](http://www.cs.bsu.edu/~hergin)
- Summer helper: [Keith DeSimini](https://www.linkedin.com/in/kdesimini/)

## License

MIT License 