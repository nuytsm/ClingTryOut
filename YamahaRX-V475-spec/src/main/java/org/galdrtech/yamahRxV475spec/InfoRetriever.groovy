package org.galdrtech.yamahRxV475spec

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl
import org.fourthline.cling.binding.xml.Descriptor.Service;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

class InfoRetriever {

	String ip = "10.0.0.15"

	public static void main(String[] args) throws Exception {

		// UPnP discovery is asynchronous, we need a callback
		RegistryListener listener = new RegistryListener() {

					public void remoteDeviceDiscoveryStarted(Registry registry,
							RemoteDevice device) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Discovery started: " + device.getDisplayString()
									);
						}
					}

					public void remoteDeviceDiscoveryFailed(Registry registry,
							RemoteDevice device,
							Exception ex) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Discovery failed: " + device.getDisplayString() + " => " + ex
									);
						}
					}

					public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Remote device available: " + device.getDisplayString()
									);
						}
					}

					public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Remote device updated: " + device.getDisplayString()
									);
							println "DETAILS: "
							println device.getDetails().getProperties()
//							println "MetaPropValues: "
//							Service s = device.findServices()[0]
//							s?.
						}
					}

					public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Remote device removed: " + device.getDisplayString()
									);
						}
					}

					public void localDeviceAdded(Registry registry, LocalDevice device) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Local device added: " + device.getDisplayString()
									);
						}
					}

					public void localDeviceRemoved(Registry registry, LocalDevice device) {
						if (device.getDisplayString().contains("475")){
							System.out.println(
									"Local device removed: " + device.getDisplayString()
									);
						}
					}

					public void beforeShutdown(Registry registry) {
						System.out.println(
								"Before shutdown, the registry has devices: "
								+ registry.getDevices().size()
								);
					}

					public void afterShutdown() {
						System.out.println("Shutdown of registry complete!");

					}
				};

		// This will create necessary network resources for UPnP right away
		System.out.println("Starting Cling...");
		UpnpService upnpService = new UpnpServiceImpl(listener);

		// Send a search message to all devices and services, they should respond soon
		upnpService.getControlPoint().search(new STAllHeader());

		// Let's wait 10 seconds for them to respond
		System.out.println("Waiting 5 seconds before shutting down...");
		Thread.sleep(5000);

		// Release all resources and advertise BYEBYE to other UPnP devices
		System.out.println("Stopping Cling...");
		upnpService.shutdown();
	}

}
