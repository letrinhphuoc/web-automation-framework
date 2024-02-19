package com.phuoclet.reports;

import com.aventstack.extentreports.reporter.configuration.Theme;
import com.phuoclet.tests.AbstractTest;
import com.phuoclet.util.Constants;
import org.testng.ITestListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(Constants.RELATIVE_PROJECT_PATH + "/extent-report/ExtentReport.html");
        reporter.config().setReportName("James HTML Report");
        reporter.config().setDocumentTitle("James HTML Report");
        reporter.config().setTimelineEnabled(true);
        reporter.config().setEncoding("utf-8");
        reporter.config().setTheme(Theme.DARK);

        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Company", "Automation FC");
        extentReports.setSystemInfo("Project", "SAS Automation");
        extentReports.setSystemInfo("Team", "James VN");
        extentReports.setSystemInfo("JDK version", Constants.JAVA_VERSION);
        return extentReports;
    }
}
