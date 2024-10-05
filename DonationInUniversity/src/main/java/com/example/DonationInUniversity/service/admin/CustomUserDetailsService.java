package com.example.DonationInUniversity.service.admin;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private LoginService loginService;
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = loginService.loginAdmin(email);
//        if(user == null){
//            throw new UsernameNotFoundException("User not found");
//        }
//        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        Role role = user.getRole();
//        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        return new CustomUserDetails(user, authorities);
//    }
////    @Override
////    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////        return userRepository.findByEmail(email)
////                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
////    }
//}
