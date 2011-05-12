package au.gov.naa.digipres.spyd.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class ModuleManager {

	private PluginManager pluginManager;

	private Map<String, SpydModule> modules;
	private List<SpydModule> loadedModules;
	private List<SpydModule> unloadedModules;

	public ModuleManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		loadedModules = new Vector<SpydModule>();
		unloadedModules = new Vector<SpydModule>();
		modules = new HashMap<String, SpydModule>();
	}

	public void addModules(List<SpydModule> moduleList) {
		for (SpydModule mod : moduleList) {
			addModule(mod, true);
		}
	}

	private void addModuleToUnloadedList(SpydModule module) {
		if (!unloadedModules.contains(module)) {
			unloadedModules.add(module);
		}
	}

	private void removeModuleFromUnloadedList(SpydModule module) {
		if (unloadedModules.contains(module)) {
			unloadedModules.remove(module);
		}
	}

	private void addModuleToLoadedList(SpydModule module) {
		if (!loadedModules.contains(module)) {
			loadedModules.add(module);
		}
	}

	private void removeModuleFromLoadedList(SpydModule module) {
		if (loadedModules.contains(module)) {
			loadedModules.remove(module);
		}
	}

	public void addModule(SpydModule module, boolean replaceExisting) {
		if (replaceExisting) {
			// Simply add the command, which will replace a command if it already exists. 
			modules.put(module.getName(), module);
		} else {
			if (!modules.containsKey(module.getName())) {
				modules.put(module.getName(), module);
			}
		}
	}

	public Set<String> listAllModules() {
		return modules.keySet();
	}

	public List<String> listLoadedModules() {
		List<String> nameList = new Vector<String>();
		for (SpydModule mod : loadedModules) {
			nameList.add(mod.getName());
		}

		return nameList;
	}

	public List<String> listUnloadedModules() {
		List<String> nameList = new Vector<String>();
		for (SpydModule mod : loadedModules) {
			nameList.add(mod.getName());
		}

		return nameList;
	}

}
