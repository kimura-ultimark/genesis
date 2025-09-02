package jp.co.ultimark.genesis.gradle.plugin.mockup;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

import jp.co.ultimark.genesis.gradle.plugin.mockup.task.MockupDeployerTask;

/**
 * モックアップをSpring Boot管理下リソースに自動展開するGradleプラグイン
 */
public class GenesisMockupPlugin implements Plugin<Project> {

	/** モックアップの格納先ディレクトリ */
	public static final String MOCKUP_SOURCE_DIR = "src/main/resources/mockup";
	/** HTMLの展開先ディレクトリ */
	public static final String HTML_DESTINATION_DIR = "src/main/resources/templates";
	/** 静的リソース（CSS・JavaScript・画像ファイル 等）の展開先ディレクトリ */
	public static final String STATICS_DESTINATION_DIR = "src/main/resources/static";
	/** タスクグループ名 */
	public static final String TASK_GROUP_NAME = "Genesis Mockup";
	
	
	@Override
	public void apply(Project project) {
		
		/* モックアップ展開タスク登録 */
		TaskProvider<MockupDeployerTask> deployerTask = project
				.getTasks()
				.register(MockupDeployerTask.TASK_NAME, MockupDeployerTask.class);
		
		deployerTask.configure(config -> {
			config.setGroup(TASK_GROUP_NAME);
		});
	}
}