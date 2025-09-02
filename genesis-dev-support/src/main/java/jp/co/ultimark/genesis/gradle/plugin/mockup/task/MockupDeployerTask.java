package jp.co.ultimark.genesis.gradle.plugin.mockup.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import jp.co.ultimark.genesis.gradle.plugin.mockup.GenesisMockupPlugin;
import jp.co.ultimark.genesis.gradle.plugin.mockup.exception.MockupDeploymentException;

/**
 * モックアップをSpring Boot管理下リソースに展開するタスク
 */
public class MockupDeployerTask extends DefaultTask {
	
	/** HTMLファイル（と見做されるファイル）の拡張子 */
	private static final String[] HTML_EXTENTIONS = new String[] {".html", ".htm"};
	
	/** タスク名 */
	public static final String TASK_NAME = "deployMockup";
	
	/** モックアップ格納先ディレクトリ */
	private String sourceDirPath;
	
	/** モックアップ展開先ディレクトリ */
	private String htmlDestDirPath;
	
	/** 静的リソース展開先ディレクトリ */
	private String staticsDestDirPath;
	
	public MockupDeployerTask() {
		this.sourceDirPath = GenesisMockupPlugin.MOCKUP_SOURCE_DIR;
		this.htmlDestDirPath = GenesisMockupPlugin.HTML_DESTINATION_DIR;
		this.staticsDestDirPath = GenesisMockupPlugin.STATICS_DESTINATION_DIR;
	}
	
	@TaskAction
	public void depoly() {
		
		/* モックアップ格納先ディレクトリ取得 */
		final File sourceDir = new File(getProject().getProjectDir(), sourceDirPath);
		// ディレクトリが存在しない場合は処理終了
		if (!sourceDir.exists()) {
			return;
		}
		
		/* ファイル処理 */
		processFile(sourceDir, sourceDir);
	}
	
	public void sourceDir(String path) {
		
		this.sourceDirPath = path;
	}
	
	public void htmlDestDir(String path) {
		
		this.htmlDestDirPath = path;
	}
	
	protected void processFile(File sourceDir, File root) {
		
		for (File file : root.listFiles()) {
			
			// ディレクトリの場合は次のファイルへ
			if (file.isDirectory()) {
				
				processFile(sourceDir, file);
				continue;
			}
			
			// 展開先ディレクトリ取得
			final File destinationDir = isHtml(file)
					? new File(getProject().getProjectDir(), htmlDestDirPath)
					: new File(getProject().getProjectDir(), staticsDestDirPath);
			
			// 展開先Fileオブジェクト生成
			final File destinationFile = new File(
					destinationDir, 
					file.getAbsolutePath().replace(sourceDir.getAbsolutePath(), ""));
			
			// 親ディレクトリが存在しない場合は親ディレクトリを含むディレクトリ階層を作成
			if (!destinationFile.getParentFile().exists()) {
				
				if (!destinationFile.getParentFile().mkdirs()) {
					
					throw new MockupDeploymentException(
							"creating directory %s failed".formatted(destinationFile.getParentFile().getAbsolutePath()), 
							new NoSuchFileException(destinationFile.getParentFile().getAbsolutePath()));
				}
			}
			
			try {
				
				// Spring Boot管理下に展開
				Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				
				throw new MockupDeploymentException(
						"Mockup resource deployment failed. source file : %s destination file %s"
						.formatted(file.getAbsolutePath(), destinationFile.getAbsolutePath())
						, e);
			}
		}
	}
	
	protected boolean isHtml(File file) {
		
		boolean isHtml = false;
		
		for (String extention : HTML_EXTENTIONS) {
			
			isHtml = file.getName().endsWith(extention);
			if (isHtml) break;
		}
		
		return isHtml;
	}
}