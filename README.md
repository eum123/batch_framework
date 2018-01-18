# batch_framework




# 기타
1. java.lang.NoSuchMethodError: org.hibernate.engine.spi.SessionFactoryImplementor.getProperties()Ljava/util/Properties 문제
  - hibernate-core 5.2.x 와 querydsl 3.x.x 버전을 사용했는데 발생됨
    아래 버전으로 변경후 정상 동작됨
  
  
          <dependency>
              <groupId>org.hibernate</groupId>
              <artifactId>hibernate-core</artifactId>
              <version>5.1.11.Final</version>
          </dependency>
  
          <dependency>
              <groupId>com.querydsl</groupId>
              <artifactId>querydsl-jpa</artifactId>
              <version>4.1.4</version>
          </dependency>
  
          <dependency>
              <groupId>com.querydsl</groupId>
              <artifactId>querydsl-apt</artifactId>
              <version>4.1.4</version>
          </dependency>