import SwiftUI
import shared

var ImageUsecases = shared.ImageUsecases.shared

struct ContentView: View {
    let generateImage = ImageUsecases.generateImage
    
    @State
    var path: URL? = nil
    
	var body: some View {
        VStack {
            if path != nil {
                AsyncImage(url: path)
            }
            Spacer()
            Button(action: { generate() }, label: {
                Text("Generate")
            })
        }
	}
    
    func generate() {
        path = nil
        generateImage.generate(prompt: "Cat", completionHandler: { image, err in
            guard let image = image else { return }
            path = URL(string: "file://" + image.path)
        })
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
