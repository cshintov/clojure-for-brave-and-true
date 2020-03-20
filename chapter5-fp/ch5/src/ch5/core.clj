(ns ch5.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn two-comp
  [f g]
  (fn [& args ] 
    (f (apply g args))))

((two-comp inc *) 2 3)

(defn mycomp
  [& functions]
  (do
    (println functions)
    (fn [& args] 
      (apply (reduce two-comp
                     identity
                     functions) args))))
;;  (apply comp functions args))
((mycomp inc dec) 1)
((mycomp #(+ % 2) inc * ) 2 3)

(defn sleepy-id [x]
  (Thread/sleep 1000)
  x)

(time (sleepy-id "Me"))

(def memoized-sleepy-id (memoize sleepy-id))

(time (memoized-sleepy-id "Me"))
(time (memoized-sleepy-id "Me"))

(defn fib [n]
  (if (< n 2)
    n
    (+' (fib (- n 1)) (fib (- n 2))
     )))

(def fib (memoize fib))

(def character 
  {:name "Shinto"
   :attributes {
                :intelligence 10
                :strength 4
                :dexterity 5}})

((comp :intelligence :attributes) character)


(defn an-attr [char attr key]
  ((comp key attr) char))

(def myattr (partial an-attr character :attributes))

(defn attr [key]
  ((comp key :attributes) character))

(myattr :strength)
(myattr :intelligence)

(=
 (myattr :dexterity)
 (attr :dexterity))

(defn twofunc [f g]
  (fn [& args]
    (f (apply g args))))

(defn mycomp [& functions]
  (reduce twofunc
          functions))

((mycomp inc #(* 2 %) *) 1 2)

(defn myassoc-in
  [m [k & ks] v]
  (println m k ks v)
  (if (or (= ks nil) (= ks '(())))
    (assoc m k v)
    (assoc m k (myassoc-in (k m) ks v))))

(myassoc-in {} [:a] 1)
(assoc-in {:a {:b {:c {} :x 1}}} [:a :b :c :d] 1)

(= (assoc-in {} [:a :b :d] 1)
   (myassoc-in {} [:a :b :d] 1))

(def p {:id 1 :char {:name "Shinto" :age 31 :height 6}})

(update-in p [:char :age] (fnil inc 0))

(defn myupdate-in
  [m [k & ks] f & args]

  (def func #(apply f % args))

  (if (or (= ks nil) (= ks '(())))
    (assoc m k (func (k m)))
    (assoc m k (myupdate-in (k m) ks func))))

(myupdate-in p [:id] inc)

(= (myupdate-in p [:char :age] + 2)
   (update-in p [:char :age] + 2))

(or (= 1 1) (= 1 2))
(func 2)
(println fst)
