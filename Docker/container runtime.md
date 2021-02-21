## Container Runtime

CRI (Container Runtime Interface)를 이해하기 위해서는 컨테이너 런타임에 대해 먼저 알아야 합니다. 
컨테이너를 실행하기 위해서는 `이미지 다운로드` -> `이미지 번들로 압축해제` -> `번들에서 컨테이너를 실행` 과 같은 세단계를 거쳐야 합니다. 도커는 컨테이너의 표준화를 위해 필요한 모든 단계가 아닌 세번째 단계인 컨테이너의 실행부분만 표준화 하였고 이로 인해 컨테이너의 런타임은 실제 실행하는 저수준 컨테이너 런타임인 OCI 런타임과 컨테이너 이미지 전송 및 관리, 이미지 압축 풀기 등을 실행하는 고수준 컨테이너 런타임으로 나뉘게 되었습니다.


### 저수준 컨테이너 런타임(Low-Level Container Runtimes)
> 저수준 컨테이너 런타임은 namespace와 croup을 설정한 다음 해당 namespace 및 cgroup 내에서 명령을 실행합니다.

- OCI를 준수하는 저수준 컨테이너 런타임으로 가장 잘 알려진 것은 runC입니다. runC는 원래 도커에서 컨테이너를 실행하기 위해 개발 되었으나, OCI 런타임 표준을 위해 독립적인 라이브러리로 사용되었습니다. 저수준 컨테이너 런다임은 컨테이너를 실제 실행하는 역할을 하지만 이미지로부터 컨테이너를 실행하려면 이미지와 관련된 API같은 기능이 필요합니다. 이러한 기능은 고수준 컨테이너 런타임에서 제공이 됩니다.

    ![](https://raw.githubusercontent.com/ippontech/blog-usa/master/images/2016/06/dockerrunc-3.png)
<br>
<br>

### 고수준 컨테이너 런타임(High-Level Container Runtimes)

- 일반적으로 고수준 컨테이너 런타임은 원격 애플리케이션이 컨테이너를 논리적으로 실행하고 모니터링 하는데 사용할 수 있는 데몬 및 API를 제공합니다.또한 컨테이너를 실행하기 위해 저수준 런타임 위에 배치됩니다.

이처럼 컨테이너를 실행하려면 저수준 및 고수준 컨테이너 런타임이 필요하기 때문에 OCI 런타임과 함께 도커가 그 역할을 했습니다. docker는 docker-containerd라는 가장 잘 알려진 고수준 컨테이너 런타임을 제공합니다. containerd도 runC와 마찬가지로 도커에서 컨테이너를 실행하기 위해 개발되었으나 나중에는 독립적인 라이브러리로 추출 되었습니다.

아래의 그림은 저/고 수준 컨테이너 런타임 관계와 도커 아키텍쳐에 대한 그림입니다.
![](https://postfiles.pstatic.net/MjAyMTAyMjFfMTIy/MDAxNjEzODg4NzMzMDk0.UIDLuXp-go6a1_RyT3JThn6cek-pVDRxslIKQnyya2Ig.XeLEEliIJ9iUNf99vJp8rw24HRTVrDtCYTpzLrCU5Lkg.PNG.ahreum0412/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2021-02-21_%EC%98%A4%ED%9B%84_3.24.59.png?type=w966)

<br><br>

### CRI (Container Runtime Interface)

- CRI는 쿠버네티스에서 만든 컨테이너 런타임 인터페이스로 개발자들의 컨테이너 런타임 구축에 대한 진입장벽을 낮추어 줍니다. 초기 쿠버네티스는 컨테이너를 실행하기 위해 도커를 사용하였는데 이는 쿠버네티스 클러스터 워커 노드의 에이전트인 Kubelet 소스코드 내부에 통합되어 있었습니다. 이처럼 통합된 프로세스는 Kubelet에 대한 깊은 이해를 필요로 하였고 쿠버네티스 커뮤니티에 상당한 유지보수 오버헤드를 발생시켰습니다. 이러한 문제를 해결하기 위해서 쿠버네티스는 CRI를 만들어 명확하게 정의된 추상화 계층을 제공함으로써 개발자가 컨테이너 런타임 구축에 집중 할 수 있게 해주었습니다.

아래의 사진은 Kubelet 동작 흐름과 CRI 구조(?)에 관한 사진입니다.

![](https://postfiles.pstatic.net/MjAyMTAyMjFfMTY4/MDAxNjEzODg5MTEwMzc0.mODm8n0CLq0Nvl7chdcSzDPnxzpVqoaCWMF-I7h0Oksg.pMqAXL7J925mHJOlaYnP8uahZjy2km9f4-vLpxLnSlIg.PNG.ahreum0412/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2021-02-21_%EC%98%A4%ED%9B%84_3.31.13.png?type=w966)


## 컨테이너의 새로운 생태계 

CRI가 만들어진 후 주요 플랫폼 벤더들은 본격적으로 컨테이너 런타임 구축에 힘을 썼습니다. 

이 이후의 내용은 도커 및 컨테이너에 대한 학습이 1차적을 끝난다면 추가적으로 공부해야겠다. 지금 다루기엔 아직 나에겐 너무 어려운 내용인듯

[Reference](https://www.samsungsds.com/kr/insights/docker.html)
