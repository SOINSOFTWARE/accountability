package co.com.soinsoftware.accountability.report;

import co.com.soinsoftware.accountability.entity.Report;

public class ThreadGenerator extends Thread {

	private Report balanceReport;

	public ThreadGenerator(final Report balanceReport) {
		super();
		this.balanceReport = balanceReport;
		this.setPriority(Thread.MAX_PRIORITY);
		this.setName("Report Generator");
	}

	@Override
	public void run() {
		System.out.println("Starting report generation");
		if (this.balanceReport != null) {
			this.generateReceiptReport();
		}
		System.out.println("Finishing report generation");
	}

	public void generateReceiptReport() {
		final Balance balance = new Balance(this.balanceReport);
		balance.generate();
	}
}