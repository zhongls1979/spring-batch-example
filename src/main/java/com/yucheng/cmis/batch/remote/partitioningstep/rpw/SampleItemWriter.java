package com.yucheng.cmis.batch.remote.partitioningstep.rpw;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class SampleItemWriter implements ItemWriter<String> {

	public volatile static int count = 0;
	private int chunkCount = 0;
	private boolean exceptionThrown = false;
	private String exceptionText;
	private String writerName = "unknown-writer";

	@Override
	public void write(final List<? extends String> items) throws Exception {
		for (String item : items) {
			count++;
			if (!exceptionThrown && exceptionText != null && item.contains(exceptionText)) {
				exceptionThrown = true;
				// throw checked exception so master can deal with it
				throw new RuntimeException("A contrived exception thrown on the remote writer to demonstrate error handling over message queues for item " + item);
			}
			System.out.println("[" + writerName + "]writing item: " + item);

		}
		chunkCount++;
	}

	public void setExceptionText(final String exceptionText) {
		this.exceptionText = exceptionText;
	}

	public void setWriterName(final String writerName) {
		this.writerName = writerName;
	}

	public int getChunkCount() {
		return chunkCount;
	}

}
