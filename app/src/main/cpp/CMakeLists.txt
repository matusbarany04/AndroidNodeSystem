cmake_minimum_required(VERSION 3.4.1)
add_library(
    native-lib
    SHARED
    native-lib.cpp)
#add_library(
#        native-opengl
#        SHARED
#        opengl.cpp)

find_library(
        native-lib
        log)

#find_library(
#        native-opengl
#        log)
target_link_libraries(
    native-lib
    ${log-lib})

#target_link_libraries(
#        native-opengl
#        ${log-lib})