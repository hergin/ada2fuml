# ada2fuml

This project reads Ada specification files (*.ads) or XML equivalents in a folder and produces fUML XMI that is compatible with MagicDraw. The project currently has two parts. The second part of the project visualizes any XML document via a template file. More documentation is upcoming.

## Prerequisites

- Java 1.8 (or above)
- **gnat2xml.exe** in the **PATH** variable. (Required only if `--ads` flag will be used.)

## Usage

Just download the latest jar file from [here](https://github.com/hergin/ada2fuml/releases) and put it in the same folder where you *.ads files stay. Run it like below by command line (assuming java is the executable java of version 1.8 or above)

> java -jar ada2fuml.jar

### ADS parameter

The tool scans and processes xml files by default. You can change it to scan ads file with `--ads` parameter like below.

> java -jar ada2fuml.jar --ads

## Console Logs

The output is printed to the console. You can save it to a file using the command below to send it by email.

> java -jar ada2fuml.jar > logs.txt

## Limitations

The tool currently is not processing all elements in Ada source code. It is customized to the project sponsor's needs. If you want to handle more structures in Ada, you should modify Extractor to handle them.

## Thanks

This project is implemented with the support provided by Raytheon Company.

## Contributors

- Main design & code: [Huseyin Ergin](http://www.cs.bsu.edu/~hergin)
- Summer helper: [Keith DeSimini](https://www.linkedin.com/in/kdesimini/)
- Sponsor/XMI output: [Roy Bell](https://www.linkedin.com/pub/roy-bell/1/32b/19a)


## License

MIT License 
