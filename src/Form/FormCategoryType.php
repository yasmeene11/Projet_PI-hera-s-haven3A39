<?php

namespace App\Form;

use App\Entity\Category;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType ;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FormCategoryType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            
            
            ->add('Product_Type', ChoiceType::class, [
                'choices' => [
                    'Food supplies' => 'Food supplies',
                    'Medical ' => 'Medical ',
                    'hygienic' => 'hygienic',
            
                ],
                'placeholder' => 'Select Type',
                'required' => true,
            ])
            
            ->add('Product_Source', ChoiceType::class, [
                'choices' => [
                    'Donation' => 'Donation',
                    'supplier ' => 'supplier ',
            
                ],
                'placeholder' => 'Select Source',
                'required' => true,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Category::class,
        ]);
    }
}