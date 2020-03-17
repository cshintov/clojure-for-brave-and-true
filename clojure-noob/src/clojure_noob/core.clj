(ns clojure-noob.core
  (:gen-class))

(defn -main
 
  [& args]
  (println "I am a little teapot!"))

(println "hello world")
(* 2 5)

(defn train
  []
  (println "choo choo!"))

(* 1 2 3)

(if false
  (do (println "Success!")
      "By Zeus!")
  (do (println "Failure!")
      "By Aquaman!"))

(when true
  (println "Success!")
  "Abracadabra!")

(defn error-message
  [severity]
  (str "Oh god! It's a disaster! We're "
       (if (= severity :mild)
         "mildly inconvenienced!"
         "dooomed!")))


(error-message :mild)

(defn sq [x]
  (* x x))

(defn sos [x y]
  (+ (sq x) (sq y)))

(sos 3 4)

(defn inc-maker
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)

(def dalmation-list ["pongo", "Perdita", "puppy1", "puppy2"])
(let [[pongo & dalmations] dalmation-list]
  [pongo dalmations])


(defn mylast
  [[fst & rst]]
  (if (= nil rst)
    fst
    (mylast rst)))

(mylast [1 2 3])
(mylast [1 2])
(mylast [])


(defn greater [a b] (if (>= a b) a b))

(defn mymax
  ([] nil) 
  ([fst & nums]
   (if (= nums nil)
     fst
     (reduce (fn [a b] (if (>= a b) a b)) fst nums))))

(mymax 3 14 5)

(mymax 1)

(mymax)

(defn dec-maker [n]
  (fn [a] (- a n)))

(def dec9 (dec-maker 9))

(dec9 11)

(defn mapset [f seq]
  (set (map f seq)))

(mapset #(+ % 1) [1 1 2 2])

(defn myprint [x]
  (println x (type x) )
  x)

(map myprint {:a 1 :b 2 :c 3})


(defn modVal [mapA func]
  (reduce 
   (fn [new [k v]]
     (assoc new k (func v))) 
   {} 
   mapA))

(def mapX {:max 30 :min 10})

(modVal mapX inc)

(defn myMap [f coll]
  (reduce
   (fn rf [new elem]
     (into new [(f elem)]))
   []
   coll))

(myMap inc '(2 3))

(defn even
  ([] (even 0))
  ([n] (cons n (lazy-seq (even (+ n 2))))))

(take 2 (even 3))

;; returns a sequence
(map identity {:a 2})
;; converts the sequence back into map
(into {} (map identity {:a 2}))

(type (into {} [[:a 1] [:b 3]]))

(conj {} [[:a 1] [:b 2]])

(conj [0] 1 2)

(apply max [1 2])

(apply conj [0] [1 2])

(= (apply map vector [[:a :b] [:c :d]])
   (map vector [:a :b] [:c :d]))

;; (apply f a b c .... seq)
(= 
 (apply + [1 2 3 4])
 (apply + 1 [2 3 4])
 (apply + 1 2 [3 4])
 (apply + 1 2 3 [4]))

(map (partial + 10) [1 2 3])

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))
(def emergency (partial lousy-logger :emergency))

(warn "Red light ahead!")
(emergency "Red light ahead!")

(defn mycomplement
  [fun]
  (fn [& args] 
    (not (apply fun args))))

(neg? 1)
(def mypos? (mycomplement neg?))
(mypos? 1)
