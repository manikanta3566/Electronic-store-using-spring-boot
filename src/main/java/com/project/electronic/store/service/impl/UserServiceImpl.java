package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.FileResponse;
import com.project.electronic.store.dto.ListingResponse;
import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.entity.User;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.UserRepository;
import com.project.electronic.store.service.FileService;
import com.project.electronic.store.service.UserService;
import com.project.electronic.store.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService , UserDetailsService {

    @Value("${image.path.user}")
    private  String basePath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setUserImagePath(userDto.getUserImagePath());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public ListingResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort="ASC".equalsIgnoreCase(sortDir)?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> users = userRepository.findAll(pageable);
        ListingResponse<UserDto> listingResponse = CommonUtil.getListingResponse(users, UserDto.class);
        return listingResponse;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setUserImagePath(userDto.getUserImagePath());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        try {
            Path path=Paths.get(user.getUserImagePath());
            Files.delete(path);
            userRepository.delete(user);
        }catch (Exception e){
            log.error("error while deleting user {}",e.getMessage());
            throw new GlobalException("failed to delete user",HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByUsernameContaining(keyword);
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public FileResponse userImageUpload(MultipartFile file, String id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
            String extension = CommonUtil.getExtensionFromFile(file);
            if (!CommonUtil.imageFileExtensions.contains(extension)) {
                throw new GlobalException("file extension " + extension + " not allowed", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
            }
            String fileName = CommonUtil.getFileNameWithTimestamp(file)+ extension;
            Path path = Paths.get(basePath).resolve(fileName);
            user.setUserImagePath(String.valueOf(path));
            userRepository.save(user);
            return fileService.uploadFile(file, path);
    }

    @Override
    public byte[] getUserImageResources(String userId) {
        byte[] bytes=null;
        try {
            User user = userRepository.findById(userId).
                    orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
            Resource fileResource = fileService.getFileResource(Paths.get(user.getUserImagePath()));
            bytes=FileCopyUtils.copyToByteArray(fileResource.getInputStream());
        }catch (Exception e){
         log.error("error while reading user image resources");
        }
        return bytes;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        return user;
    }
}
