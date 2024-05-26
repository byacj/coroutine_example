코루틴 예제


## 코루틴

### 코루틴이란?

루틴은 작업의 집합체를 가르키는 용어로 사용됨.

- 메인 루틴 : 프로그램 전체의 개괄적인 동작 절차를 표시하도록 만들어진 루틴.
- 서브 루틴 : 반복적인 특정 기능을 모아서 별도로 묶인 루틴.

코루틴도 루틴의 일종. 코(co-work 하는)루틴으로 생각하면 됨.

**코루틴 디버깅 찍기**

VM option에 -Dkotlinx.coroutines.debug 을 넣어주면 실행되고 있는 coroutine의 이름을 찍어줌.

![coroutine_vm옵션.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/4183210b-6c21-4f33-9f9b-d20d4d7bfb83/coroutine_vm%EC%98%B5%EC%85%98.png)

더 알아보기

**코루틴(coroutine)에 대하여. 1편. 코루틴을 왜 쓸까?** : https://eocoding.tistory.com/88

**비선점 멀티태스킹 :** https://jongmin92.github.io/2021/03/21/Kotlin/coroutines/

## 코루틴 빌더와 JOB

코루틴 빌더는 코루틴을 생성하고 응답값으로 JOB을 리턴한다. 

1. **runBlocking**
    
    runBlocking은 코루틴이 완료될때까지 스레드를 점유함. 본인 스레드 뿐만 아니라 자식 스레드가 모두 완료될 때 까지 스레드를 점유. 작업을 더 이상 남아있지 않아도 자식 스레드의 작업이 끝날때 까지 스레드를 점유 하기 때문에 잘 못 사용하면 성능에 안좋은 영향을 미칠 수 있음.
    
    ![coroutine_runBlocking.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/f05eb372-e562-4a1a-8aab-ec6dceacda88/coroutine_runBlocking.png)
    
2. **launch 와 async**
    
    launch와 async는 동시성 작업을 생성할 때 사용. launch는 작업의 결과를 사용할 필요가 없을때 사용하고 async는 작업의 결과를 다른 다른 작업에서 사용할 때 사용. launch는 Job객체를 리턴하고 async는 Deferred객체를 리턴 함. 
    
    deferred는 Job을 상속한 하위타입. 코루틴이 수행한 값을 포함하는 job이라고 생각하면 됨. await()를 통해 계산결과에 접속 할 수 있음.
    
    ![스크린샷 2024-05-26 155704.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/c11f91fc-ea34-4837-b5c9-ec56930784b8/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-26_155704.png)
    
    runBlocking과 lauch의 사용 예시 (코드 : sample7)
    
    ![coroutine_runBlocking&launch.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/af8c7f83-2f3a-4e60-b183-5bed796bc093/coroutine_runBlockinglaunch.png)
    
3. **join()과 joinall()**
    1. join은 단건의 job을 인자로 받아 인자로 받은 한개의 job 완료된 후에 다음 코드가 실행 됨 (코드 : sample8)
        
        ![coroutine_join.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/c4cb57d8-13ed-49c6-88b4-4d85d474486a/coroutine_join.png)
        
    2. joinAll은 복수의 job을 인자로 받아 인자로 받은 모든 Job이 완료된 후에 다음 코드가 실행 됨 (코드 : sample9)
        
        ![coroutine_joinall.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/c08332d5-0087-405e-a236-3e7519f4c7c9/coroutine_joinall.png)
        
    
    “작업완료”가 “이미지1 변환 완료”보다 뒤에 찍히는 것에서 보듯이 joinAll()이 호출된 지점에 uploadImageJob뿐만 아니라 runBlocking으로 선언된 main스레드도 joinAll()이 끝날때까지 대기를 하게 됨
    
4. **await(), awaitAll()**
    1. await() : 단건의 deferred에서 계산 결과값을 가져옴.
        
        (코드 :  Sample10)
        
        ![coroutine_await.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/6d3d6da2-43b5-4717-8eb6-487ffaae2b7a/coroutine_await.png)
        
    2. awaitall() : deferred 배열을 인자로 받아서 인자로 받은 deferred를 모두 수행할 때까지 잠시 작업을 중단. deferred가 모두 수행되면 그 값을 받아서 다음 작업 수행.
        
        (코드 :  Sample11)
        
        ![coroutine_awaitall.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/11228164-2fba-4902-b151-9d949cbae765/coroutine_awaitall.png)
        
        awaitAll()의 인자로 받은 각 deferred는 같은 타입을 리턴해야 함. 
        
        ![coroutine_awaitall_1.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/fbea18d7-dde1-4eb4-855e-6526f90907bb/coroutine_awaitall_1.png)
        
        awaitAll()로 받은 deferred 중 다른 타입을 리턴하는 deferred가 있다면 타입오류가 발생함.
        
        (코드 :  Sample12)
        
        ![coroutine_awaitall_2.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/44a7bf3d-8ecf-41dc-885c-d0572422daf6/coroutine_awaitall_2.png)
        

### 코루틴의 start 옵션

코루틴은 생성을 한다고 해서 항상 바로 실행하지 않음. 옵션에 따라서 실행이 시점이 달라짐

- **CoroutineStart.DEFAULT** - 기본 옵션.  생성과 함께 별도 명령없이 실행이 가능한 시점에 코루틴이 시작이 됨. 시작할 기회를 얻기 전에 취소되면 취소가 된다.
- **CoroutineStart.ATOMIC** - 성과 함께 별도 명령없이 실행이 가능한 시점에 코루틴이 시작이 됨.  기회를 얻기 전에 취소되어도 시작이 됨. 그러나 중단점이 발생하면 그 중단점에서 취소됨.
- **CoroutineStart.LAZY** - 생성시점에 바로 시작되지 않고, job에서 start 메서드를 실행시켜주어야 시작. 시작되기전에 취소가 되면 취소됨.
- **CoroutineStart.UNDISPATCHED** - 생성과 함께 별도의 명령이 없어도 시작.  시작하기 전에 취소를 명령해도 실행. 현재 쓰레드에서 작업이 실행.

CoroutineStart.LAZY를 사용한 예 (코드 : sample2)

![coroutine_lazystart.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/206b8b5a-9a88-47a9-a29b-831f1a949bef/coroutine_lazystart.png)

## CoroutineContext와 코루틴의 상태

### CoroutineContext의 구성요소

1. coroutineName : 코루틴의 이름
2. couroutineDispatcher : 자신에게 실행 요청된 코루틴들을 작업 대기열에 적재하고, 자신이 사용할 수 있는 스레드에 코루틴을 할당하여 코루틴이 실행할 수 있도록 하는 역할
    
    ![coroutineDispatcher.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/9b4c2b33-e87a-4fa1-aa43-dc1b86fea0a9/coroutineDispatcher.png)
    
3. job : 코루틴의 상태를 추적하고 제어하는 역할
4. coroutineExceptionHandler : 코루틴내의 코드 실행 중 발생하는 Exception을 처리 할 수 있는 Handler

### CoroutineContext의 구성요소 사용

코루틴의 구성요소는 Element를 상속받음. 

![coroutine_element.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/f19a0202-9cc2-4495-88a0-a33599c16429/coroutine_element.png)

Element는 CoroutineContext를 상속 받았기 때문에 코루틴 빌더로 코루틴을 생성할때 일부 요소만을 이용하여 CoroutineContext를 설정할 수 있음.

![coroutine_coroutineContext사용예1.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/5602bad7-0f5c-4249-887a-ec4894ad539e/coroutine_coroutineContext%EC%82%AC%EC%9A%A9%EC%98%881.png)

CoroutineContext의 구성요소는 Element의 get()과 key값을 이용해서 가져올 수 있음. CoroutineContext을 설정할때 +(plus() 메서드 이용)를 이용하여 설정.

(코드 : sample1)

![coroutineContext 구성요소 이용.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/a3305d45-5c4a-4ff1-a33b-ae5eb523f400/coroutineContext_%EA%B5%AC%EC%84%B1%EC%9A%94%EC%86%8C_%EC%9D%B4%EC%9A%A9.png)

### 코루틴의 상태

![coroutine_status.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/a4881cbc-59cc-4dca-992f-08c9eddc69d2/coroutine_status.png)

- 생성(NEW) : 코루틴이 생성된 상태. 아직 실행되지 않음.
- 활성(Active) :  코루틴이 실행되고 있는 상태.
- 실행 완료 중 (Completing) :  코루틴이 완료되었으나 자식 코루틴의 실행이 완료되지 않아 기다리는 상태. 코루틴은 자신 코루틴이 완료되고 자식 코루틴까지 모두 완료되는 시점에 완료가 됨. 부모 코루틴의 마지막 코드까지 수행을 완료하였어도 자식 코루틴이 모든 수행을 완료를 해야 부모 코루틴의 동작이 완료가 됨.
- 실행 완료 (Completed) : 실행이 완료 됨.
- 취소 중(Cancelling) :  취소가 진행중인 상태.
- 취소 완료(Cancelled) : 취소가 완료된 상태.

### 코루틴의 상태를 확인 할 수 있는 함수

- isActive : 코루틴이 활성화가 되어 있는지 확인
- isCancelled : 코루틴이 취소가 되어 있는지 확인
- isCompleted : 코루틴이 완료가 되어 있는지 확인

**코루틴 상태별 함수 값**

![코루틴 state 함수.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/3f6f4c38-d12a-4431-888c-6b78fa5fd0bc/%EC%BD%94%EB%A3%A8%ED%8B%B4_state_%ED%95%A8%EC%88%98.png)

**코루틴 상태별 함수 확인**

(코드 : sample3)

![coroutine_상태함수.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/95affb01-0ade-43b0-9900-03dd5bcf53e8/coroutine_%EC%83%81%ED%83%9C%ED%95%A8%EC%88%98.png)

- job1은 생성에  `start = CoroutineStart.*LAZY*` 옵션을 주었음. 생성만 된 상태고 아직 실행되지 않았기 때문에 isActive가 false로 나옴.
- job2는 생성과 동시에 실행 하였기 때문에 isCompleted가 true로 나옴(코루틴 시작시점에 default는 생성과 함께 시작)
- job3는 본인 자체는 모든 코드가 수행이 되었으나 자식 코루틴이 실행 중이기 때문에 Completing상태.  (부모는 모든 자식 코루틴이 완료가 될때까지 기다림)

### 코루틴의 상태를 확인하는 함수의 사용 예시 및 주의 점

(코드 : sample4)

![coroutine_cancel_무한루프.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/5207f32b-fae2-4ab2-a015-9b895e35e800/coroutine_cancel_%EB%AC%B4%ED%95%9C%EB%A3%A8%ED%94%84.png)

job을 취소했지만 이미 수행중인 잡은 계속 수행. 코루틴은 중단점이 발생이 될때까지 취소 상태를 확인하지 않음. 때문에 job을 취소했지만 계속 루프문을 수행함.

- 중단점 : yeild(), suspend fun을 호출 하는 시점.

(코드 : sample5)

![coroutine_cancel_yeild.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/cda2a44d-1c55-40de-8547-7bc6c42958c2/coroutine_cancel_yeild.png)

위의 코드는 루프문 수행완료 시점 마다 yield()를 호출함. 중단점이후 다시 코루틴을 실행하면서 코루틴의 상태를 확인하고 취소상태인 코루틴인 것을 확인하기 때문에 루프문을 종료.

(코드 : sample6)

![coroutine_cancel_isActive사용.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/2b642979-c60d-41be-be27-1c0e2a47c09a/07775ae3-f63f-4697-857b-6db9594bf4f4/coroutine_cancel_isActive%EC%82%AC%EC%9A%A9.png)

while문의 조건을 보면 this.isActive인 것을 볼 수 있음. this는 CoroutineScope이고 CoroutineScope.isActive는 CoroutineScope내의 job의 isActive를 이용하여 코루틴이 실패했는지 확인함. 코루틴의 상태는 Job에 저장이 되고 CoroutineScope와 CoroutineCotext는 내부의 Job에 접근하여 코루틴의 상태를 확인 함.

더 알아보기 

코루틴 상태 값 : [https://origogi.github.io/coroutine/잡/](https://origogi.github.io/coroutine/%EC%9E%A1/)

coroutineContext : https://jaejong.tistory.com/62

coroutinBuilder : https://jaejong.tistory.com/64

withContext : https://todaycode.tistory.com/183

CoroutineDispatcher : https://todaycode.tistory.com/182

## 

참조 : 코틀린 코루틴의 정석
