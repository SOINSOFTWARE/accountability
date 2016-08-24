package co.com.soinsoftware.accountability.report;

import co.com.soinsoftware.accountability.entity.Report;

public class ThreadGenerator extends Thread {

	private final Report report;

	private final boolean isBalanceReport;

	public ThreadGenerator(final Report report, final boolean isBalanceReport) {
		super();
		this.report = report;
		this.isBalanceReport = isBalanceReport;
		this.setPriority(Thread.MAX_PRIORITY);
		this.setName("Report Generator");
	}

	@Override
	public void run() {
		System.out.println("Starting report generation");
		if (this.report != null) {
			this.generateReport();
		}
		System.out.println("Finishing report generation");
	}

	public void generateReport() {
		final AccountabilityReport balance = new AccountabilityReport(
				this.report, this.isBalanceReport);
		balance.generate();
	}
}