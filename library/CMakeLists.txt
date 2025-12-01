cmake_minimum_required(VERSION 3.4.1)

add_library( 
             nes-core
             SHARED
             src/main/cpp/jni.cpp
             src/main/cpp/AndroidAudioFairy.cpp
             src/main/cpp/AndroidGamepadFairy.cpp
             src/main/cpp/AndroidVideoFairy.cpp
             src/main/cpp/buffer-queue/src/BufferQueue.cpp
             src/main/cpp/Cycloa/src/emulator/Cartridge.cpp
             src/main/cpp/Cycloa/src/emulator/file/NesFile.cpp
             src/main/cpp/Cycloa/src/emulator/Processor.cpp
             src/main/cpp/Cycloa/src/emulator/Audio.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper4.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper1.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper2.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper3.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper0.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/board/VRC4.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper25.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper23.cpp
             src/main/cpp/Cycloa/src/emulator/mapper/Mapper21.cpp
             src/main/cpp/Cycloa/src/emulator/Video.cpp
             src/main/cpp/Cycloa/src/emulator/VirtualMachine.cpp
             src/main/cpp/Cycloa/src/emulator/fairy/FairyConstantValues.cpp
             src/main/cpp/Cycloa/src/emulator/exception/EmulatorException.cpp
           )

find_library( log-lib log )
find_library( graphic-lib jnigraphics )
find_library( open-sles-lib OpenSLES )

target_link_libraries( nes-core ${log-lib} ${graphic-lib} ${open-sles-lib} )
