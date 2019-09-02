#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'modified_share'
  s.version          = '0.0.1'
  s.summary          = 'A new Flutter plugin that allows sharing text, images via popular Social Networks such as Facebook, WhatsApp, Instagram etc.'
  s.description      = <<-DESC
A new Flutter plugin that allows sharing text, images via popular Social Networks such as Facebook, WhatsApp, Instagram etc.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'

  s.ios.deployment_target = '8.0'
end

