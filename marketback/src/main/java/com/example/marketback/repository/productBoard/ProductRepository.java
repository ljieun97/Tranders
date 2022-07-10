package com.example.marketback.repository.productBoard;

import com.example.marketback.entity.chatting.ChatRoom;
import com.example.marketback.entity.productBoard.ProductBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductBoard, Long> {

    @Query("select p from ProductBoard p where p.productNo = :productNo")
    ProductBoard findByProductNo(Long productNo);

    @Query("select p from ProductBoard p where p.member.city.villageName = :region and (p.content like %:keyWord% or p.title like %:keyWord%)")
    List<ProductBoard> findAllByVillageNameContentContain(String keyWord, String region, Pageable pageable);
  
    @Query("select p from ProductBoard p where p.member.city.district = :region and p.content like %:keyWord%")
    List<ProductBoard> findAllByDistrictContentContain(String keyWord, String region, Pageable pageable);
  
    @Query("select p from ProductBoard p where p.member.city.city = :region and p.content like %:keyWord%")
    List<ProductBoard> findAllByCityContentContain(String keyWord, String region, Pageable pageable);

    @Query("select p from ProductBoard p where p.member.memberNo = :memberNo")
    List<ProductBoard> findByMemberNoInProductBoard(Long memberNo);

    @Query("select p from ProductBoard p join p.buyer pb where pb.memberNo = :memberNo")
    List<ProductBoard> findByBuyer(@Param("memberNo") Long memberNo);


    @Query("select p from ProductBoard p where p.member.city.villageName = :region")
    List<ProductBoard> findByVillageNameMemberContain(String region);

    @Query("select p from ProductBoard p where p.member.city.district = :region")
    List<ProductBoard> findByDistrictMemberContain(String region);

    @Query("select p from ProductBoard p where p.member.city.city = :region")
    List<ProductBoard> findByCityMemberContain(String region);


    List<ProductBoard> findByContentContaining(String keyWord);

    @Query("select p from ProductBoard p where p.member.city.villageName = :region and p.content like %:keyWord%")
    List<ProductBoard> findAllByVillageNameProductKeywordContain(String keyWord, String region);

    @Query("select p from ProductBoard p where p.member.city.district = :region and p.content like %:keyWord%")
    List<ProductBoard> findAllByDistrictProductKeyWordContain(String keyWord, String region);

    @Query("select p from ProductBoard p where p.member.city.city = :region and p.content like %:keyWord%")
    List<ProductBoard> findAllByCityProductKeywordContain(String keyWord, String region);

    @Query("select p from ProductBoard p where p.member.id = :id")
    List<ProductBoard> findByMemberId(String id);
}
