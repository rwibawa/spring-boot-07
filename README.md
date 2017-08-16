# spring-boot-07
## 1. Initialize with spring-boot cli
```bash
  $ spring init -d=web spring-boot-07
  $ cd spring-boot-07
```

## 2. Add _Greeting.java_
```java
package com.example.springboot07;

public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
```

## 3. Add _GreetingController.java_
```java
package com.example.springboot07;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
            String.format(template, name));
    }
}
```

## 4. Run it
```bash
  $ cd src/main/resources/static/
  $ bower install

  $ mvn spring-boot:run -Dserver.port=8094
```

Open it on the browser [http://localhost:8094](http://localhost:8094)

## 5. Setup Angular UI
```bash
$ cd src/main/resources/static/
$ npm init

$ mkdir assets
$ mkdir assets/vendor
$ mkdir assets/js
$ mkdir assets/images
$ mkdir assets/styles
$ mkdir assets/includes

$ echo '{"directory" : "assets/vendor"}' > .bowerrc
$ npm install bower --save

$ bower init
$ bower install bootstrap --save
$ bower install angular --save
$ bower install angular-ui --save
$ bower install font-awesome --save
```

### 6. Debugging on javascript console
```javascript
  > angular.element($0).controller();
```