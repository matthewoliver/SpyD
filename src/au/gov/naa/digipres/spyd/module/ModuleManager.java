package au.gov.naa.digipres.spyd.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.command.CommandManager;
import au.gov.naa.digipres.spyd.core.SpydPreferences;
import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class ModuleManager {

	public static final int DEFAULT_THREAD_POOL_SIZE = 5;

	private PluginManager pluginManager;

	private Map<String, SpydModule> modules;
	private List<SpydModule> loadedModules;
	private List<SpydModule> unloadedModules;

	private List<String> modulesAutoLoad;

	// Where worker threads stand idle
	private Vector<ModuleWorkerThread> threads;

	// initial number of worker threads. More workers will be added if there are none available for a request.
	public static int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

	public ModuleManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		loadedModules = new Vector<SpydModule>();
		unloadedModules = new Vector<SpydModule>();
		modules = new HashMap<String, SpydModule>();

		syncAutoLoadModules();
	}

	public void syncAutoLoadModules() {
		modulesAutoLoad = new Vector<String>();

		SpydPreferences prefs = pluginManager.getSpyd().getPreferences();
		String autoLoad = prefs.getPreference(SpydPreferences.AUTO_LOAD_MODULES);

		if (autoLoad != null) {
			String[] mods = autoLoad.split(",");

			for (String mod : mods) {
				mod = mod.trim();
				modulesAutoLoad.add(mod);
			}
		}

		if (modulesAutoLoad.size() > 0) {
			// attempt to autoload modules
			for (String mod : modulesAutoLoad) {
				if (!modules.containsKey(mod)) {
					// module doesn't exist... yet or typo.
					continue;
				} else {
					try {
						loadModule(modules.get(mod));
					} catch (ModuleException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}

	public void addModules(List<SpydModule> moduleList) throws ModuleException {
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

	private void unloadCommandsFromModule(SpydModule module) {
		CommandManager commandManager = pluginManager.getCommandManager();

		for (Command command : module.getModuleCommands()) {
			commandManager.removeCommand(command);
		}
	}

	private void addModuleToLoadedList(SpydModule module) {
		if (!loadedModules.contains(module)) {
			loadedModules.add(module);

			pluginManager.getCommandManager().addCommands(module.getModuleCommands());
		}
	}

	private void removeModuleFromLoadedList(SpydModule module) {
		if (loadedModules.contains(module)) {
			loadedModules.remove(module);

			unloadCommandsFromModule(module);
		}
	}

	public synchronized void loadModule(String module) throws ModuleException {
		if (modules.containsKey(module)) {
			loadModule(modules.get(module));
		} else {
			throw new ModuleException("Module '" + module + "' not found");
		}
	}

	public synchronized void loadModule(SpydModule module) throws ModuleException {
		if (loadedModules.contains(module)) {
			throw new ModuleException("Module '" + module.getName() + "' already loaded!");
		} else if (!unloadedModules.contains(module)) {
			throw new ModuleException("Module '" + module.getName() + "' doesn't exist!");
		}

		removeModuleFromUnloadedList(module);
		addModuleToLoadedList(module);

		//TODO actually start executing the module.
	}

	public synchronized void unloadModule(String module) throws ModuleException {
		if (modules.containsKey(module)) {
			unloadModule(modules.get(module));
		} else {
			throw new ModuleException("Module '" + module + "' not found");
		}
	}

	public synchronized void unloadModule(SpydModule module) throws ModuleException {
		if (!loadedModules.contains(module)) {
			throw new ModuleException("Module '" + module.getName() + "' isn't loaded!");
		}

		module.unloadEvent();
		removeModuleFromLoadedList(module);
		addModuleToUnloadedList(module);
	}

	public void addModule(SpydModule module, boolean replaceExisting) throws ModuleException {
		if (replaceExisting) {
			// Simply add the command, which will replace a command if it already exists. 
			modules.put(module.getName(), module);
			unloadedModules.add(module);
		} else {
			if (!modules.containsKey(module.getName())) {
				modules.put(module.getName(), module);
				unloadedModules.add(module);
			}
		}

		if (modulesAutoLoad.contains(module.getName())) {
			loadModule(module);
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

	// === The running modules section of the code. ===
	public List<ModuleWorkerThread> getWorkerPool() {
		return threads;
	}

}
