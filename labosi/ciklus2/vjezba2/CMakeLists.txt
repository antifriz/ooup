project(c2v2)

file(GLOB SOURCE_FILES_BAD "badboy.cpp")
add_executable(${PROJECT_NAME}.bad ${SOURCE_FILES_BAD})

file(GLOB SOURCE_FILES_GOOD "goodgirl.cpp")
add_executable(${PROJECT_NAME}.good ${SOURCE_FILES_GOOD})
