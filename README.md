# log_generator
simple log generator for telecom
generated random log telephone calls
For run need dictionary (example in resources/dictionary)

Example arguments:
java -jar log_generator.jar 100 10 20 ./dictionary ./output
100 - amount string in output logs
10 - amount random number in phone number in logs
20 - amount word in sms messages in logs
./dictionary - path to dictionary for generate sms messages in logs
./output - path to output log file

If use without arguments default dictionary fnd output uses from directory with jar file