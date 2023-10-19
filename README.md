# Private School Web Automation
# Headless - How to Run The Tests in Headless Mode

1. Clone the repo
2. Open the project in Eclipse/Intellij
3. Open project explorer.
4. Update pom.xml maven file.
5. Go to the TestNG file name runTest.xml. You can find it following path : src/test/resources/runner/runTest.xml
6. Right click in the open file and select Run As > TestNG Suite
7. The tests will start running.

# UI - How to Run The Tests in UI Mode

1. Go to file src/test/main/base/BaseClass.java
2. Go to function 'initConfiguration()'.
3. Comment/Remove the line : 
options.addArguments("--headless");
4. Go to the TestNG file name runTest.xml in the path src/test/resources/runner/runTest.xml
5. Right click in the open file and select Run As > TestNG Suite
6. The tests will starts running in UI Mode.

# How to Specify which tests you want to run

1. Open the file src/test/resources/data/SuiteTests_Web.xlsm
2. Change flag column values of each required test to 'Yes' and non-required tests to 'No'.
3. Click on Run Suite button if you have Windows OS and Maven is installed and configured. 

	# How to configure maven on windows if not installed
	1. Download and extract maven from
	https://maven.apache.org/download.cgi
	2. Copy path to maven bin folder
	3. Right click 'This PC'
	4. Click Properties > Advanced System Settings > Environment Variables 
	5. In 'System Variables' click on 'Path'
	6. Click on 'Edit'
	7. Click on 'New'
	8. Add path to extracted maven bin folder 
	9. Click 'OK' > 'OK' > 'OK' 
	10. Restart system
	11. For Mac please follow this link : https://www.baeldung.com/install-maven-on-windows-linux-mac
	
Other OS users can follow either 'Headless' or 'UI' procedure to run tests.

# How to View Report
1. The report is generated in the HTML format.
2. Refresh project.
3. Go to 'reports' folder.
4. Open the file named "Extent_Report.html" in your favourite browser.
5. The Report will be shown with screenshots.
