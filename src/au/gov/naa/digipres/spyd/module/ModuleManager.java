package au.gov.naa.digipres.spyd.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.command.Command;
import au.gov.naa.digipres.spyd.command.CommandManager;
import au.gov.naa.digipres.spyd.core.Constants;
import au.gov.naa.digipres.spyd.core.SpydPreferences;
import au.gov.naa.digipres.spyd.plugin.PluginManager;

public class ModuleManager {

	public static final int DEFAULT_THREAD_POOL_SIZE = 5;

	private PluginManager pluginManager;

	private Map<String, SpydModule> modules;
	private List<SpydModule> loadedModules;
	private List<SpydModule> unloadedModules;

	private List<String> modulesAutoLoad;

	private Logger logger;

	// Where worker threads stand idle
	private List<ModuleWorkerThread> idleThreads;

	// Using a map as a module may need to use more then 1 thread, a map means it can use a unique ID (it must manage itself)
	// to communicate/manage or whatever.  
	private Map<String, ModuleWorkerThread> runningModuleThreads;

	// initial number of worker threads. More workers will be added if there are none available for a request.
	public static int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

	public ModuleManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		logger = Logger.getLogger(Constants.ROOT_LOGGING_PACKAGE);
		loadedModules = new Vector<SpydModule>();
		unloadedModules = new Vector<SpydModule>();
		modules = new HashMap<String, SpydModule>();

		syncAutoLoadModules();

		initModuleServer();
	}

	private void initModuleServer() {
		idleThreads = new Vector<ModuleWorkerThread>();
		runningModuleThreads = new ConcurrentHashMap<String, ModuleWorkerThread>();

		for (int i = 0; i < threadPoolSize; ++i) {
			ModuleWorkerThread mwt = new ModuleWorkerThread(this);
			new Thread(mwt, "Module worker #" + i).start();
			idleThreads.add(mwt);
		}
	}

	/***
	 * Sync all the auto load modules list with the modules that are running, that is
	 * load any modules from the list that are not yet started. 
	 */
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

	/***
	 * Add a list of SpydModules to the manager.
	 * @param moduleList
	 * @throws ModuleException
	 */
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

	/**
	 * Load the selected module.
	 * @param module The module to load
	 * @throws ModuleException
	 */
	public synchronized void loadModule(String module) throws ModuleException {
		if (modules.containsKey(module)) {
			loadModule(modules.get(module), module);
		} else {
			throw new ModuleException("Module '" + module + "' not found");
		}
	}

	/**
	 * Load the selected module, use the unique ID to reference the thread. 
	 * @param module The module to add
	 * @param uniqueId The unique ID to use.
	 * @throws ModuleException
	 */
	public synchronized void loadModule(String module, String uniqueId) throws ModuleException {
		if (modules.containsKey(module)) {
			loadModule(modules.get(module), uniqueId);
		} else {
			throw new ModuleException("Module '" + module + "' not found");
		}
	}

	/**
	 * Load the selected module.
	 * @param module The module to load.
	 * @throws ModuleException
	 */
	public synchronized void loadModule(SpydModule module) throws ModuleException {
		loadModule(modules.get(module), module.getName());
	}

	/**
	 * Load the selected module, use the unique ID to reference the thread.
	 * @param module The module to load
	 * @param uniqueId The unique ID to use.
	 * @throws ModuleException
	 */
	public synchronized void loadModule(SpydModule module, String uniqueId) throws ModuleException {
		if (loadedModules.contains(module)) {
			throw new ModuleException("Module '" + module.getName() + "' already loaded!");
		} else if (!unloadedModules.contains(module)) {
			throw new ModuleException("Module '" + module.getName() + "' doesn't exist!");
		}

		removeModuleFromUnloadedList(module);
		addModuleToLoadedList(module);

		// Actually load the module.
		synchronized (idleThreads) {
			ModuleWorkerThread mwt;
			if (idleThreads.isEmpty()) {
				// If the idle threads are empty then the inital workers are busy so create a new thread
				mwt = new ModuleWorkerThread(this);
				new Thread(mwt, "Additional module thread").start();
				runningModuleThreads.put(uniqueId, mwt);
			} else {
				// Get the first worker and start it.
				mwt = idleThreads.get(0);
				idleThreads.remove(0);
				runningModuleThreads.put(uniqueId, mwt);
			}
			mwt.setModule(module);
		}
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

		// stop and remove all instances of the module from running, sometimes more then one module thread could be 
		// running so we need to remove them all.
		for (String key : runningModuleThreads.keySet()) {
			if (runningModuleThreads.get(key).getModule().equals(module)) {
				// shut this thread down
				runningModuleThreads.get(key).stopWorker();

				// Remove this one from the running threads. 
				runningModuleThreads.remove(key);
			}
		}
	}

	public SpydModule getLoadedModule(String moduleName) throws ModuleException {
		for (SpydModule mod : loadedModules) {
			if (mod.getName().equals(moduleName)) {
				return mod;
			}
		}

		throw new ModuleException("Module '" + moduleName + "' not loaded or doesn't exist");
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
		return idleThreads;
	}

	public Map<String, ModuleWorkerThread> getRunningModuleThreads() {
		return runningModuleThreads;
	}

	public Collection<ModuleWorkerThread> getRunningModuleThreadsAsList() {
		return runningModuleThreads.values();
	}

	public ModuleWorkerThread getRunningModuleThreadByID(String id) {
		if (runningModuleThreads.containsKey(id)) {
			return runningModuleThreads.get(id);
		}

		return null;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}
}
