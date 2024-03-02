<?php

namespace App\Repository;

use App\Entity\DonationM;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<DonationM>
 *
 * @method DonationM|null find($id, $lockMode = null, $lockVersion = null)
 * @method DonationM|null findOneBy(array $criteria, array $orderBy = null)
 * @method DonationM[]    findAll()
 * @method DonationM[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class DonationMRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, DonationM::class);
    }

//    /**
//     * @return DonationM[] Returns an array of DonationM objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('d.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?DonationM
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
public function findByDonationAmount($donationAmount)
{
    return $this->createQueryBuilder('d')
        ->andWhere('d.Donation_Amount LIKE :donationAmount')
        ->orWhere('d.donationM_Date LIKE :donationAmount')
        ->setParameter('donationAmount', '%' . $donationAmount . '%')
        ->getQuery()
        ->getResult();
}
public function searchMF($searchValue)
    {
        // Implement your search logic here, for example:
        return $this->createQueryBuilder('d')
            ->andWhere('d.Donation_Amount LIKE :searchValue')
            ->setParameter('searchValue', '%' . $searchValue . '%')
            ->getQuery()
            ->getResult(\Doctrine\ORM\Query::HYDRATE_ARRAY); // Return results as an array
    }

}