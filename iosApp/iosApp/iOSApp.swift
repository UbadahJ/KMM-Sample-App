import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        shared.IOSConfig()
            .koin()
            .logger()
            .build()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
