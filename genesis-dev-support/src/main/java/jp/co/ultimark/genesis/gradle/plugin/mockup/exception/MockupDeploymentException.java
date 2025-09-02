package jp.co.ultimark.genesis.gradle.plugin.mockup.exception;

public class MockupDeploymentException extends RuntimeException {

	/** エラー原因となった例外オブジェクト */
	private Throwable cause;
	
	/** エラーメッセージ */
	private String message;
	
	/**
	 * エラーメッセージ、エラー原因となった例外オブジェクトを指定してMockupDeploymentExceptionを構築する。
	 * 
	 * @param message エラーメッセージ
	 * @param cause エラー原因となった例外オブジェクト
	 */
	public MockupDeploymentException(String message, Throwable cause) {
		this.cause = cause;
		this.message = message;
	}
	
	@Override
	public synchronized Throwable getCause() {

		return this.cause;
	}
	
	@Override
	public String getMessage() {
		
		return this.message;
	}
	
	@Override
	public String getLocalizedMessage() {
		
		return this.message;
	}
}