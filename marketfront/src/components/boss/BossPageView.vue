<template>
  <div id="my_page_box" style="position: relative;">
    <div id="profile_back_img">
      <div style="width: 100%; height: 400px; position: relative;">
        <div v-if="backProfileImgs === ''">
          <v-img src="@/assets/bossProfile/back/Tranders_boss_base_backProfile.png" id="img" style="width: 100%; height: 400px;"  @click="backProfileImgDialog = true"></v-img>
        </div>
        <div v-else>
        <swiper class="swiper" :options="swiperOption" style="height: 400px">

          <swiper-slide v-for="(boardImg,index) in backProfileImgs" :key="index">
            <v-img :src="require(`@/assets/bossProfile/back/${boardImg.fileName}`)" id="img"></v-img>
          </swiper-slide>

          <div class="swiper-pagination" slot="pagination"></div>
        </swiper>
        </div>
      </div>

      <div style=" top: 25px; padding-left: 5px; position: absolute; z-index: 1">
        <v-btn icon @click="backPage"><v-icon x-large>mdi-chevron-left</v-icon></v-btn>
      </div>
      <div v-if="backProfileImgs !== ''" style="top: 25px; position: absolute; left: 80%; z-index: 1">
        <v-btn text @click="modify"><v-icon x-large>mdi-camera</v-icon>&nbsp; &nbsp;수정</v-btn>
      </div>
    </div>

    <div id="profile" style="padding-top: 30px">

      <div id="profile_img">
        <v-img :src="require(`@/assets/bossProfile/front/${boss.profileImg}`)" height="200px" width="200px" @click="changeImg" style="border-radius: 70%"></v-img>
        <input type=file class="file-input" id="files" ref="files" multiple v-on:change="fileUpload()" style="display: none">
      </div>
      <div style="padding-left: 55px; font-size: 40px; width: 100%;" class="pa-5">
        {{ boss.placeName }}
        <div style="font-size: 20px" class="pa-2">
          {{ boss.region }} ㆍ {{boss.category}} ㆍ 단골 0
        </div>
      </div>
      <div>
        <v-btn depressed color="success" height="40" type="submit" @click="bossRemoveDialog = true"><b>사장님 프로필 삭제</b></v-btn>
      </div>
    </div>

    <div style="padding-bottom: 50px; height: 40px; padding-top: 30px">
      <div style="float: left; height: 40px; width: 33%; font-size: 25px; text-align: center;" @click="home = true; comm = false; review=false;">
        홈
        <div v-if="home" style="border-bottom: 3px solid green">
        </div>
      </div>

      <div style="float: left; height: 40px; width: 33%; font-size: 25px; text-align: center" @click="home = false; comm = true; review=false;">
        커뮤니티 {{boss.communityCount}}
        <div v-if="comm" style="border-bottom: 3px solid green">
        </div>
      </div>

      <div style="float: left; height: 40px; width: 33%; font-size: 25px; text-align: center" @click="home = false; comm=false; review = true;">
        리뷰 {{boss.reviewCount}}
        <div v-if="review" style="border-bottom: 3px solid green">

        </div>
      </div>

    </div>


    <div v-if="home">
      <boss-home-view :boss="boss" @savePrice="savePrice" @saveCoupon="saveCoupon" @modifySave="modifySave" @modifyCoupon="modifyCoupon"></boss-home-view>
    </div>

    <div v-if="comm">
      <boss-comm-list-view :bossNo="bossNo"></boss-comm-list-view>
    </div>

    <div v-if="review">
      <boss-review-list-view :bossNo="bossNo"></boss-review-list-view>
    </div>

      <div style="background-color: rgba(187,187,187,0.23); height: 100px; text-align: center; font-size: 15px" class="pa-10">
        오이마켓에 등록된 가게의 정보는 가게 관리자가 직접 등록한 것으로 (주)오이마켓은 등록된 내용에 대하여 일체의 책임을 지지 않습니다.
      </div>

    <v-dialog width="500" v-model="changeImgDialog">
      <v-card style="width: 500px; position: relative">
        <v-row justify="center" style="width: 500px; padding-bottom: 10px">
          <v-card-title>이미지 변경</v-card-title>
        </v-row>

        <v-row style="height: 300px; width: 500px;" justify="center">
          <div style="width: 300px; height: 250px; justify-content: center; display: flex; align-items: center">
            <v-btn @click="onClickFile" icon v-if="changeIsCheck">
              <v-img src="@/assets/plus.png" style="height: 200px; width: 250px"> </v-img>
            </v-btn>

            <div class="itemFileBox" ref="itemFileBox">
              <input type="file" class="item-file-image" id="uploadItemFile" ref="uploadItemFile" @change="onFileSelected" accept="image/*" style="display: none"/>
              <label for="uploadItemFile">
                <div class="wrapper-image">
                  <img ref="uploadItemImage" alt="" src=""/>
                </div>
              </label>
            </div>
          </div>
        </v-row>

        <v-row justify="end" no-gutters>
          <v-card-actions>
            <v-btn depressed height="40" type="submit" @click="changeImgDialog = false"><b>Close</b></v-btn>
            <v-btn depressed color="success" height="40" type="submit" @click="saveProfile"><b>Save</b></v-btn>
          </v-card-actions>
        </v-row>
      </v-card>
    </v-dialog> <!-- 이미지 프로필 변경 -->

    <v-dialog v-model="backProfileImgDialog" width="1500">
      <v-card style="height: 800px; border: 1px solid rgba(197,197,197,0.16)">
        <div class="main-container">
          <div class="room-deal-information-container">
            <div class="room-deal-information-title">사진 등록</div>
            <div class="room-picture-notice">
              <ul class="room-write-wrapper">
                <li>
                  사진은 가로로 찍은 사진을 권장합니다. (가로 사이즈 최소 800px)
                </li>
                <li>사진 용량은 사진 한 장당 10MB 까지 등록이 가능합니다.</li>

              </ul>
            </div>
            <div class="room-file-upload-wrapper">
              <div v-if="!files.length" class="room-file-upload-example-container">
                <div class="room-file-upload-example">
                  <div class="room-file-image-example-wrapper">이미지</div>
                  <div class="room-file-notice-item">
                    실사진 최소 3장 이상 등록하셔야 하며, 가로사진을 권장합니다.
                  </div>
                  <div class="room-file-notice-item room-file-notice-item-red">
                    로고를 제외한 불필요한 정보(워터마크,상호,전화번호 등)가 있는 매물은 비공개처리됩니다
                  </div>
                  <div class="room-file-notice-item room-file-upload-button">
                    <div class="image-box">
                      <label for="file">일반 사진 등록</label>
                      <input type="file" id="file" ref="files" @change="imageUpload" multiple />
                    </div>
                  </div>
                </div>
              </div>
              <div v-else class="file-preview-content-container">
                <div class="file-preview-container">
                  <div v-for="(file, index) in files" :key="index" class="file-preview-wrapper">
                    <div class="file-close-button" @click="fileDeleteButton" :name="file.number">
                      x
                    </div>
                    <img :src="file.preview" />
                  </div>
                  <div class="file-preview-wrapper-upload">
                    <div class="image-box">
                      <label for="file">추가 사진 등록</label>
                      <input type="file" id="file" ref="files" @change="imageAddUpload" multiple />
                    </div>

                  </div>
                </div>
              </div>
            </div>
          </div>

          <div style="padding: 20px 20px 20px 20px">
            <v-btn block depressed color="success" height="50" type="submit" @click="saveBackProfile"><h3><b>저장</b></h3></v-btn>
<!--            <v-btn style="width: 100%; height: 50px; font-size: 30px" class="light-green lighten-3"  @click="saveBackProfile">저장</v-btn>-->
          </div>

        </div>
      </v-card>
    </v-dialog>  <!--뒷 배경 변경-->

    <v-dialog v-model="backProfileImgModifyDialog" width="1500px">
      <v-card width="1500px" height="auto" class="pa-4">
        <div style="padding: 5px 20px 5px 20px">

          <v-row justify="center">
            <v-card-title>삭제할 사진을 선택해주세요</v-card-title>
          </v-row>

          <v-row>
            <div style="overflow-x: scroll; display: flex">
              <div v-for="(img, index) in backProfileImgs" :key="img.no">
                <div style="display: flex;">
                  <div style="padding: 5px 5px 5px 5px">
                    <div><v-img min-width="100" min-height="100" style="width: 200px; height: 180px" :src="require(`@/assets/bossProfile/back/${img.fileName}`)"></v-img></div>
                    <v-checkbox v-model="checkBox" :value="index"></v-checkbox>
                  </div>
                </div>
              </div>
            </div>
          </v-row>

          <div class="image_upload_box" style="display: flex; padding-top: 40px">
            <div @click="addImg" style="width: 200px; height: 150px; border: 1px solid black; border-radius: 7px; display: flex; flex-direction: column; justify-content: center; align-items: center ">
              <div><v-img width="50px" height="50px" src="@/assets/icon/camera.png"></v-img></div>
              <div><span style="color: #06850e">{{backProfileImgs.length + files.length}}</span> / 10</div>
            </div>
            <input type="file" id="file" ref="files" @change="imageUpload" multiple style="display: none"/>

            <div style="display: flex; overflow: scroll">
              <div v-for="(file, index) in files" :key="index" class="file-preview-wrapper">
                <div class="file-close-button" @click="fileDeleteButton" :name="file.number">
                  x
                </div>
                <img :src="file.preview"  alt=""/>
              </div>
            </div>
          </div>
        </div>

        <v-row justify="end" no-gutters>
          <v-card-actions>
            <v-btn depressed height="40" type="submit" width="100" style="padding-right: 8px" @click="backProfileImgDialog = false"><b>취소</b></v-btn>
            <v-btn depressed color="success" height="40" width="100" type="submit" @click="modifyBackProfile"><b>저장</b></v-btn>
          </v-card-actions>
        </v-row>

      </v-card>
    </v-dialog>

    <v-dialog width="500" style="max-height: 400px" v-model="bossRemoveDialog">
      <v-card style="width: 500px; position: relative; max-height: 400px">
        <v-row justify="center" style="width: 500px; padding-top: 30px">
          <v-card-title>사장님 탈퇴</v-card-title>
        </v-row>

        <v-row style="width: 500px; height: 100px" justify="center">
          <div style="width: 450px; justify-content: center; display: flex; align-items: center">
            경고: 사장님 삭제는 취소할 수 없습니다.<br/>
            삭제하면 사장 프로필의 모든 정보가 삭제됩니다. 삭제하시겠어요?
          </div>
        </v-row>

        <v-row justify="end" no-gutters>
          <v-card-actions>
            <v-btn depressed height="40" type="submit" @click="bossRemoveDialog = false"><b>취소</b></v-btn>
            <v-btn depressed color="red" class="white--text" height="40" type="submit" @click="removeBoss"><b>삭제</b></v-btn>
          </v-card-actions>
        </v-row>
      </v-card>
    </v-dialog> <!-- 탈퇴 경고 -->
    </div>

</template>

<script>
import cookies from "vue-cookies";
import {mapActions, mapState} from "vuex";
import 'swiper/css/swiper.css'
import {Swiper, SwiperSlide} from "vue-awesome-swiper";
import BossHomeView from "@/components/boss/BossHomeView";
import BossReviewListView from "@/components/boss/BossReviewListView";
import BossCommListView from "@/components/boss/BossCommListView";

export default {
  name: "BossPageView",
  props: {
    boss: {
      type: Object
    }
  },
  components:{
    BossCommListView,
    BossReviewListView,
    BossHomeView,
    Swiper, SwiperSlide
  },
  data(){
    return {
      home: true,
      comm: false,
      review: false,
      backProfileImg: false,
      id: cookies.get("id"),
      bossNo: '',
      changeImgDialog: false,
      backProfileImgDialog: false,
      backProfileImgModifyDialog: false,
      bossRemoveDialog: false,
      itemImageInfo: {
        uploadImages: ''
      },
      file: null,
      changeIsCheck: true,
      files: [],
      filesPreview: [],
      uploadImageIndex: 0,
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 30,
        autoHeight: true,
        loop: false,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      },
      checkBox: [],

    }
  },
  methods: {
    ...mapActions(['fetchBossBackProfile']),
    modifyBackProfile(){
      let formData = new FormData;

      if(this.checkBox.length !== 0) { // 이미지 삭제하겠다.
        for (let i = 0; i < this.checkBox.length; i++) {
          formData.append('deleteImg', this.backProfileImgs[this.checkBox[i]].no);
        }
      }else {
        formData.append('deleteImg', 'noImg')
      }

      formData.append('bossNo', this.bossNo)

      if(this.files.length > 0) { // 이미지 추가
        for (let i = 0; i < this.files.length; i++) {
          formData.append('fileList', this.files[i].file)
        }
        formData.append('id', this.id)
        this.$emit('modifyAddBackProfile', formData)
      }else {
        this.$emit('modifyADeleteBackProfile', formData)
      }
    },
    backPage(){
      this.$router.push({name: 'MyPageProfile'})
    },
    modify(){
      this.backProfileImgModifyDialog = true
    },
    savePrice(payload) {
      this.$emit('savePrice', payload)
    },
    modifySave(payload) {
      //alert('modify')
      this.$emit('modifySave', payload)
    },
    deletePrice(payload){
      this.$emit('deletePrice', payload)
    },
    changeImg(){
      //alert('changeImg')
      this.changeImgDialog = true;
    },
    onClickFile(){
      this.$refs.uploadItemFile.click()
    },
    addImg(){
      this.$refs.files.click()
    },
    onFileSelected(event){
      let image = event.target;

      if(image.files[0]){
        this.changeIsCheck = false;
        this.file = this.$refs.uploadItemFile.files;
        console.log(this.file)
        this.fileLength = this.file.length
        let itemImage = this.$refs.uploadItemImage;

        itemImage.src = window.URL.createObjectURL(image.files[0]);

        this.itemImageInfo.uploadImages = itemImage.src;

        itemImage.width ='200';

        itemImage.onload = () => {
          window.URL.revokeObjectURL(this.src)
        }
      }
    },
    saveProfile(){
      if(this.fileLength === ''){
        alert('파일을 선택해주세요!')
      }else {
        let formData = new FormData;

        formData.append('imgFile', this.file[0])
        formData.append('id', this.id)

        this.$emit('saveBossProfileImg', formData)
      }

      this.changeImgDialog = false;
    },
    imageUpload() {
      console.log(this.$refs.files.files);

      let num = -1;
      for (let i = 0; i < this.$refs.files.files.length; i++) {
        this.files = [
          ...this.files,
          //이미지 업로드
          {
            //실제 파일
            file: this.$refs.files.files[i],
            //이미지 프리뷰
            preview: URL.createObjectURL(this.$refs.files.files[i]),
            //삭제및 관리를 위한 number
            number: i
          }
        ];
        num = i;
      }
      this.uploadImageIndex = num + 1; //이미지 index의 마지막 값 + 1 저장
      console.log(this.files);
      // console.log(this.filesPreview);
    },
    imageAddUpload() {
      console.log(this.$refs.files.files);

      // this.files = [...this.files, this.$refs.files.files];
      //하나의 배열로 넣기c
      let num = -1;
      for (let i = 0; i < this.$refs.files.files.length; i++) {
        console.log(this.uploadImageIndex);
        this.files = [
          ...this.files,
          //이미지 업로드
          {
            //실제 파일
            file: this.$refs.files.files[i],
            //이미지 프리뷰
            preview: URL.createObjectURL(this.$refs.files.files[i]),
            //삭제및 관리를 위한 number
            number: i + this.uploadImageIndex
          }
        ];
        num = i;
      }
      this.uploadImageIndex = this.uploadImageIndex + num + 1;

      console.log(this.files);
      // console.log(this.filesPreview);
    },
    fileDeleteButton(e) {
      const name = e.target.getAttribute('name');
      this.files = this.files.filter(data => data.number !== Number(name));
      // console.log(this.files);
    },
    saveBackProfile(){
      let formData = new FormData;

      for(let i = 0; i < this.files.length; i++){
        formData.append('fileList', this.files[i].file)
      }
      formData.append('id', this.id)
      formData.append('name', this.name)
      this.$emit('saveBackProfile', formData)
      this.backProfileImgDialog = false;
    },
    saveCoupon(payload){
      this.$emit('saveCoupon', payload)
    },
    modifyCoupon(payload){
      this.$emit('modifyCoupon', payload)
    },
    removeBoss(){
      let bossNo = this.boss.bossAuthNo;

      this.$emit('removeBoss', {bossNo})
    }
  },
  computed: {
    ...mapState(['backProfileImgs']),
  },
  created() {
    this.bossNo = this.boss.bossAuthNo;
    this.fetchBossBackProfile(this.boss.bossAuthNo)
  }
}
</script>

<style lang="scss" scoped>
#my_page_box{
  align-items: center;
  width: 100%;
  max-width: 800px;
  min-width: 800px;
  margin: 0 auto;
  padding: 0 40px 0 40px;
}
#profile {
  padding: 15px 20px 20px 20px;
  display: flex;
}
.home_style {
  padding: 10px 20px 10px 20px;
}
.home_style_btn {
  padding: 10px 0 10px 0;
}
.kmap {
  width: 100%;
  height: 300px;
}
.main-container {
  width: 1200px;
  height: 400px;
  margin: 0 auto;
}

.room-deal-information-container {
  margin-top: 50px;
  color: #222222;
  border: 1px solid #dddddd;
}

.room-deal-information-title {
  text-align: center;
  font-size: 18px;
  line-height: 60px;
  border-bottom: 1px solid #dddddd;
}

.room-deal-information-content-wrapper {
  min-height: 50px;
  display: flex;
}

.room-deal-informtaion-content-title {
  font-size: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 150px;
  background-color: #f9f9f9;
}

.room-deal-information-content {
  width: 100%;
  font-size: 14px;
}

.room-deal-option-selector {
  display: flex;
  align-items: center;
  padding: 15px;
}

.room-deal-option-item {
  width: 100px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #cccccc;
  border-radius: 5px;
  cursor: pointer;
}

.room-deal-option-item:last-child {
  margin-left: 10px;
}

.room-deal-option-notice {
  margin-left: auto;
  font-size: 14px;
  color: #888888;
}

.room-deal-option-item-deposit {
  margin-left: 10px;
}

.room-deal-information-wrapper {
  display: flex;
  flex-direction: column;
}

.room-deal-information-option {
  padding: 10px;
  display: flex;
  align-items: center;
}

.room-deal-information-option:last-child {
  border-bottom: 1px solid #dddddd;
}

.room-deal-information-item-type {
  font-size: 13px;
  color: #fff;
  background-color: #61b6e5;
  min-width: 50px;
  height: 26px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 3px;
}

.room-deal-information-item-wrapper {
  display: flex;
  align-items: center;
  margin-left: 10px;
  height: 46px;
  width: 100%;
}

.room-deal-information-item-wrapper>input {
  border: 1px solid #dddddd;
  width: 140px;
  height: 100%;
  padding: 0 15px;
  font-size: 15px;
}

.room-deal-inforamtion-won {
  margin: 0 10px;
}

.room-deal-information-example {
  color: #888888;
}

.room-deal-information-option:not(:first-child) {
  margin-top: 10px;
}

.room-deal-inforamtion-divide {
  font-size: 22px;
  margin: 0 8px;
  color: #222222;
  font-weight: 100;
}

.room-deal-close-button-wrapper {
  margin-left: auto;
  cursor: pointer;
}

.room-deal-close-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 22px;
  height: 22px;
  background-color: #666666;
  color: rgb(220, 220, 220);
}

.room-deal-cliked {
  background-color: rgb(235, 235, 235);
  color: rgb(170, 170, 170);
}

.room-file-upload-example {
  height: 100%;
}

.room-write-content-container {
  border-top: 1px solid #dddddd;
  min-height: 260px;
}

.room-picture-notice {
  margin: 20px;
  padding: 20px 40px;
  border: 1px solid #dddddd;
}

.file-preview-content-container {
  height: 100%;
}

.room-file-upload-wrapper {
  margin: 20px;
  border: 1px solid #dddddd;
  background-color: #f4f4f4;
  min-height: 350px;
  font-size: 15px;
  color: #888888;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.room-file-upload-example-container {
  display: flex;
  align-items: center;
  justify-content: center;
  /* height: 100%;
width: 100%; */
}

.room-file-image-example-wrapper {
  text-align: center;
}

.room-file-notice-item {
  margin-top: 5px;
  text-align: center;
}

.room-file-notice-item-red {
  color: #ef4351;
}

.image-box {
  margin-top: 30px;
  padding-bottom: 20px;
  text-align: center;
}

.image-box input[type='file'] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.image-box label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #232d4a;
  color: #fff;
  vertical-align: middle;
  font-size: 15px;
  cursor: pointer;
  border-radius: 5px;
}

.file-preview-wrapper {
  padding: 10px;
  position: relative;
}

.file-preview-wrapper>img {
  position: relative;
  width: 190px;
  height: 130px;
  z-index: 10;
}

.file-close-button {
  position: absolute;
  /* align-items: center; */
  line-height: 18px;
  z-index: 99;
  font-size: 18px;
  right: 5px;
  top: 10px;
  color: #fff;
  font-weight: bold;
  background-color: #666666;
  width: 20px;
  height: 20px;
  text-align: center;
  cursor: pointer;
}

.file-preview-container {
  height: 100%;
  display: flex;
  flex-wrap: wrap;
}

.file-preview-wrapper-upload {
  margin: 10px;
  padding-top: 20px;
  background-color: #888888;
  width: 190px;
  height: 130px;
}

.room-write-button-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #222222;
}

.room-write-button-wrapper>div {
  width: 160px;
  height: 50px;
  border: 1px solid #dddddd;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  cursor: pointer;
}

.room-write-button {
  margin-left: 15px;
  color: #fff;
  background-color: #1564f9;
}

.room-write-button:hover {
  opacity: 0.8;
}
.swiper {
  height: 900px;
  width: 100%;

  .swiper-slide {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

#img {
  width: 100%; height: 500px;
  max-width: 100%;
  max-height: 500px;
}
.priceTitle {
  padding-bottom: 10px;
  font-size: 18px;
}
</style>