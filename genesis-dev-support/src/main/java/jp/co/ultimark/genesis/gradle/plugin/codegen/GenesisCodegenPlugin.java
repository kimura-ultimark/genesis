package jp.co.ultimark.genesis.gradle.plugin.codegen;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.seasar.doma.gradle.codegen.CodeGenPlugin;
import org.seasar.doma.gradle.codegen.extension.CodeGenConfig;

/**
 * Genesis CodeGen Plugin
 */
public class GenesisCodegenPlugin extends CodeGenPlugin {

	@Override
	public void apply(Project project) {
		
		super.apply(project);
		
		project.getExtensions().configure(CONFIGURATION_NAME, config -> {
			
			@SuppressWarnings("unchecked")
			final NamedDomainObjectContainer<CodeGenConfig> container = (NamedDomainObjectContainer<CodeGenConfig>)config;
			container.all(codeGenConfig -> {
				codeGenConfig.setGlobalFactory(new ConventionalGlobalFactory());
			});
		});
	}
}