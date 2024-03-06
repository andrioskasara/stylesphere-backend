package com.stylesphere.service.impl;

import com.stylesphere.model.Product;
import com.stylesphere.model.User;
import com.stylesphere.model.Wishlist;
import com.stylesphere.model.dto.WishlistDto;
import com.stylesphere.repository.ProductRepository;
import com.stylesphere.repository.UserRepository;
import com.stylesphere.repository.WishlistRepository;
import com.stylesphere.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    public WishlistServiceImpl(UserRepository userRepository, ProductRepository productRepository, WishlistRepository wishlistRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) {
        Optional<Product> productOptional = productRepository.findById(wishlistDto.getProductId());
        Optional<User> userOptional = userRepository.findById(wishlistDto.getUserId());
        if (productOptional.isPresent() && userOptional.isPresent()) {
            Wishlist wishlist = new Wishlist();
            wishlist.setProduct(productOptional.get());
            wishlist.setUser(userOptional.get());
            return wishlistRepository.save(wishlist).getDto();
        }
        return null;
    }

    public List<WishlistDto> getWishlistByUserId(Long userId) {
        return wishlistRepository.findAllByUserId(userId).stream().map(Wishlist::getDto).collect(Collectors.toList());
    }
}
