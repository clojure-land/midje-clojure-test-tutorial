(ns migration.core-test
  (:use clojure.test
        migration.core))

(deftest migration
  (testing "migration produces two maps with keys (and values) from one moved to the other"
    (is (= [{} {:a 1}]
           (migrate {:a 1} :a {}))))
  (testing "duplicates are not migrated"
    (is (= [{:a "not moved"} {:a "retained"}]
           (migrate {:a "not moved"} :a {:a "retained"}))))
  (testing "multiple keys are allowed"
    (is (= [{:b 2} {:a 1 :b 3}]
           (migrate {:a 1, :b 2} :a :b {:b 3}))))
  (testing "keys missing from the source are ignored"
    (is (= [{:a "kept"} {:b "moved"}]
           (migrate {:a "kept", :b "moved"} :b :c {}))))

  (testing "a rather silly test"
    (is (even? (count (migrate {:a 1} :a {}))))))
